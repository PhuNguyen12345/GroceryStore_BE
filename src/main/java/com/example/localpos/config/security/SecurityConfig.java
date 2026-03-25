package com.example.localpos.config.security;

import com.example.localpos.config.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return bcrypt.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String storedPassword) {
                if (storedPassword == null) {
                    return false;
                }

                if (storedPassword.startsWith("$2a$")
                        || storedPassword.startsWith("$2b$")
                        || storedPassword.startsWith("$2y$")) {
                    return bcrypt.matches(rawPassword, storedPassword);
                }

                return rawPassword.toString().equals(storedPassword);
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Tắt CSRF: Vì chúng ta dùng REST API stateless, không dùng Session
                // Nếu không tắt, các method POST/PUT/DELETE sẽ bị chặn 403
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Cho phép CORS: Để Frontend (Port 3000) gọi được Backend (Port 8080)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 3. Cấu hình quyền truy cập (Quan trọng nhất)
                .authorizeHttpRequests(auth -> auth
                        // public - no token required
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/v1/auth/login"),
                                new AntPathRequestMatcher("/api/v1/products"),
                                new AntPathRequestMatcher("/api/v1/products/**"),
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/swagger-ui.html"),
                                new AntPathRequestMatcher("/error"),
                                new AntPathRequestMatcher("/api/v1/categories/tree/active"),
                                new AntPathRequestMatcher("/api/v1/promotions/filter/active"),
                                new AntPathRequestMatcher("/api/v1/brands/**"),
                                new AntPathRequestMatcher("/api/v1/categories/**")
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/auth/login",
                                "/api/auth/login"
                        ).permitAll()
                        // HR: only admin/store manager can manage employees
                        .requestMatchers("/api/v1/employees/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")

                        // HR: shift & schedule management is admin/store manager only
                        // IMPORTANT: More specific patterns MUST come before general patterns
                        // Cashier and inventory staff can view their own schedule/shift data
                        .requestMatchers(HttpMethod.GET, "/api/v1/work-schedules/me", "/api/v1/work-schedules/me/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "CASHIER", "INVENTORY_STAFF")

                        // HR modification (create/update/delete) is admin/store manager only
                        .requestMatchers(HttpMethod.POST, "/api/v1/shifts/**", "/api/v1/work-schedules/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/shifts/**", "/api/v1/work-schedules/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/shifts/**", "/api/v1/work-schedules/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/shifts/**", "/api/v1/work-schedules/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")

                        // HR reading (GET) is allowed for all authenticated users so cashiers can view shift schedules
                        .requestMatchers(HttpMethod.GET, "/api/v1/shifts/**", "/api/v1/work-schedules/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "CASHIER", "INVENTORY_STAFF")

                        // Cashier can access customer functions
                        .requestMatchers("/api/v1/customers/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "CASHIER")

                        // Inventory staff can view stock by product/store
                        .requestMatchers(HttpMethod.GET, "/api/v1/inventory/stocks/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "INVENTORY_STAFF")

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        //  requires authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Cấu hình CORS (Cross-Origin Resource Sharing)
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Cho phép Frontend từ đâu gọi vào? (React thường chạy port 3000)
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*"));

        // Cho phép các method nào?
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Cho phép các header nào?
        configuration.setAllowedHeaders(List.of("*"));

        // Expose Authorization header so frontend can read JWT in responses when needed
        configuration.setExposedHeaders(List.of("Authorization"));

        // Cho phép gửi credentials (nếu cần cookie/auth header)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
