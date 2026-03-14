package com.example.localpos.modules.auth.service.impl;

import com.example.localpos.modules.auth.dto.AuthResponseDTO;
import com.example.localpos.modules.auth.dto.LoginRequestDTO;
import com.example.localpos.modules.auth.service.AuthService;
import com.example.localpos.modules.auth.service.JwtService;
import com.example.localpos.modules.hr.dto.request.EmployeeRequestDTO;
import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO register(EmployeeRequestDTO request) {
        return null;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO req) {
        Employee emp = employeeRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        if (Boolean.FALSE.equals(emp.getIsActive())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account is inactive");
        }

        if (!passwordEncoder.matches(req.getPassword(), emp.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        UserDetails principal = User.builder()
                .username(emp.getUsername())
                .password(emp.getPasswordHash())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + emp.getRole().name())))
                .build();

        return AuthResponseDTO.builder()
                .accessToken(jwtService.generateToken(principal))
                .employeeId(emp.getId())
                .username(emp.getUsername())
                .fullName(emp.getFullName())
                .role(emp.getRole())
                .build();
    }
}
