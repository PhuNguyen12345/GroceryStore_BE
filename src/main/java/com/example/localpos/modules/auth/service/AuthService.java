package com.example.localpos.modules.auth.service;

import com.example.localpos.modules.auth.dto.AuthResponseDTO;
import com.example.localpos.modules.auth.dto.LoginRequestDTO;
import com.example.localpos.modules.auth.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);
}
