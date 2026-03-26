package com.example.localpos.modules.auth.service;

import com.example.localpos.modules.auth.dto.ForgotPasswordRequest;

public interface ForgotPasswordService {
    void requestPasswordReset(ForgotPasswordRequest req);
}
