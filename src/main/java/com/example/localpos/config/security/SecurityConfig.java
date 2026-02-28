package com.example.localpos.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
                        // Cho phép TẤT CẢ request truy cập không cần đăng nhập
                        // (Dùng cho giai đoạn đầu test API)
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    // Cấu hình CORS (Cross-Origin Resource Sharing)
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Cho phép Frontend từ đâu gọi vào? (React thường chạy port 3000)
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));

        // Cho phép các method nào?
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Cho phép các header nào?
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Cho phép gửi credentials (nếu cần cookie/auth header)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
