package com.example.localpos.modules.auth.service;

import com.example.localpos.modules.auth.dto.AuthResponseDTO;
import com.example.localpos.modules.auth.dto.LoginRequestDTO;
import com.example.localpos.modules.hr.dto.request.EmployeeRequestDTO;

public interface AuthService {

    AuthResponseDTO register(EmployeeRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);
}
