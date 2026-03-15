package com.example.localpos.modules.auth.controller;

import com.example.localpos.modules.auth.dto.AuthResponseDTO;
import com.example.localpos.modules.auth.dto.LoginRequestDTO;
import com.example.localpos.modules.auth.service.AuthService;
import com.example.localpos.modules.hr.dto.request.EmployeeRequestDTO;
import com.example.localpos.modules.hr.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> register(@RequestBody @Valid EmployeeRequestDTO req) {
        employeeService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
