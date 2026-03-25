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
                // 1. Tắt CSRF: vì dùng REST API stateless, không dùng Session
                // Nếu không tắt, các method POST/PUT/DELETE có thể bị chặn 403
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Cho phép CORS: để Frontend (port 3000) gọi được Backend (port 8080)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 3. Cấu hình quyền truy cập (quan trọng nhất)
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/v1/auth/login"),
                                new AntPathRequestMatcher("/uploads/images/**"),
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/swagger-ui.html"),
                                new AntPathRequestMatcher("/error")
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/payments/bank/webhook").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public storefront data (homepage)
                        .requestMatchers(HttpMethod.GET, "/api/v1/products", "/api/v1/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/units/product/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/tree/active").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/promotions/filter/active").permitAll()

                        // Employee self/profile read (all staff roles)
                        .requestMatchers(HttpMethod.GET, "/api/v1/employees/*", "/api/v1/employees/username/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "INVENTORY_STAFF", "CASHIER")

                        // HR management (ADMIN only)
                        .requestMatchers("/api/v1/shifts/**", "/api/v1/work-schedules/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/v1/employees/**")
                        .hasAnyRole("ADMIN", "INVENTORY_STAFF")

                        // Product management write (ADMIN + STORE_MANAGER)
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**", "/api/v1/brands/**", "/api/v1/categories/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**", "/api/v1/brands/**", "/api/v1/categories/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/products/**", "/api/v1/brands/**", "/api/v1/categories/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**", "/api/v1/brands/**", "/api/v1/categories/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")

                        // POS stock read-only helpers (ADMIN + STORE_MANAGER + INVENTORY_STAFF + CASHIER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/warehouses", "/api/v1/warehouses/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "INVENTORY_STAFF", "CASHIER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/inventory/stocks/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "INVENTORY_STAFF", "CASHIER")

                        // Inventory (ADMIN + STORE_MANAGER + INVENTORY_STAFF)
                        .requestMatchers("/api/v1/suppliers/**", "/api/v1/warehouses/**", "/api/v1/inventory/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "INVENTORY_STAFF")

                        // POS (ADMIN + STORE_MANAGER + CASHIER)
                        .requestMatchers("/api/v1/pos/orders/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "CASHIER")

                        // CRM read for checkout (ADMIN + STORE_MANAGER + CASHIER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/**", "/api/v1/vouchers/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "CASHIER")

                        // POS create customer quickly at checkout (ADMIN + STORE_MANAGER + CASHIER)
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers")
                        .hasAnyRole("ADMIN", "STORE_MANAGER", "CASHIER")

                        // Promotion read (ADMIN + STORE_MANAGER)
                        .requestMatchers(HttpMethod.GET, "/api/v1/promotions/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")

                        // CRM write (ADMIN + STORE_MANAGER)
                        .requestMatchers("/api/v1/customers/**", "/api/v1/vouchers/**", "/api/v1/promotions/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")

                        // Reports + settings
                        .requestMatchers("/api/v1/reports/**")
                        .hasAnyRole("ADMIN", "STORE_MANAGER")
                        .requestMatchers("/api/v1/settings/**")
                        .hasRole("ADMIN")

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
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

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

