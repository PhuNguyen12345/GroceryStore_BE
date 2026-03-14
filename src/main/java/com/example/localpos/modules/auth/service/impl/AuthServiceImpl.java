package com.example.localpos.modules.auth.service.impl;

import com.example.localpos.modules.auth.dto.AuthResponseDTO;
import com.example.localpos.modules.auth.dto.LoginRequestDTO;
import com.example.localpos.modules.auth.service.AuthService;
import com.example.localpos.modules.auth.service.JwtService;
import com.example.localpos.modules.hr.dto.request.EmployeeRequestDTO;
import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmployeeRepository employeeRepository;

    @Override
    public AuthResponseDTO register(EmployeeRequestDTO request) {
        return null;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        UserDetails principal = (UserDetails) auth.getPrincipal();
        Employee emp = employeeRepository.findByUsername(principal.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("Employee not found: " + principal.getUsername()));

        return AuthResponseDTO.builder()
                .accessToken(jwtService.generateToken(principal))
                .employeeId(emp.getId())
                .username(emp.getUsername())
                .fullName(emp.getFullName())
                .role(emp.getRole())
                .build();
    }
}
