package com.example.localpos.modules.auth.service.impl;

import com.example.localpos.modules.auth.dto.AuthResponseDTO;
import com.example.localpos.modules.auth.dto.LoginRequestDTO;
import com.example.localpos.modules.auth.dto.RegisterRequestDTO;
import com.example.localpos.modules.auth.service.AuthService;
import com.example.localpos.modules.auth.service.JwtService;
import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository    employeeRepository;
    private final PasswordEncoder       passwordEncoder;
    private final JwtService            jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (employeeRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }
        if (request.getEmail() != null && employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        Employee employee = new Employee();
        employee.setUsername(request.getUsername());
        employee.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setRole(request.getRole());
        employee.setIsActive(true);
        employee.setCreatedAt(Instant.now());

        employeeRepository.save(employee);

        String token = generateToken(employee);

        return buildResponse(token, employee);
    }


    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        // Throws AuthenticationException (→ 401) if credentials are wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Employee employee = employeeRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUsername()));

        if (!employee.getIsActive()) {
            throw new IllegalStateException("Account is deactivated. Please contact an administrator.");
        }

        String token = generateToken(employee);

        return buildResponse(token, employee);
    }


    private String generateToken(Employee employee) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("employeeId", employee.getId());
        extraClaims.put("role",       employee.getRole().name());

        return jwtService.generateToken(extraClaims, toUserDetails(employee));
    }

    private AuthResponseDTO buildResponse(String token, Employee employee) {
        return AuthResponseDTO.builder()
                .accessToken(token)
                .employeeId(employee.getId())
                .username(employee.getUsername())
                .fullName(employee.getFullName())
                .role(employee.getRole())
                .build();
    }

    private org.springframework.security.core.userdetails.UserDetails toUserDetails(Employee employee) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(employee.getUsername())
                .password(employee.getPasswordHash())
                .roles(employee.getRole().name())
                .build();
    }
}
