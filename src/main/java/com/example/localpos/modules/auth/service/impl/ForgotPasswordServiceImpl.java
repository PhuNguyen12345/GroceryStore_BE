package com.example.localpos.modules.auth.service.impl;

import com.example.localpos.modules.auth.dto.ForgotPasswordRequest;
import com.example.localpos.modules.auth.service.ForgotPasswordService;
import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final EmployeeRepository employeeRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.admin-email}")
    private String adminEmail;

    @Override
    public void requestPasswordReset(ForgotPasswordRequest req) {
        // Check if username + email match the same account
        Employee employee = employeeRepository
                .findByUsernameAndEmail(req.username(), req.email())
                .orElseThrow(() -> new UsernameNotFoundException("Username/Email not found"));

        sendResetRequestToAdmin(employee);
    }

    private void sendResetRequestToAdmin(Employee employee) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("[POS] Password Reset Request — " + employee.getUsername());
        message.setText(buildEmailBody(employee));

        mailSender.send(message);
    }

    private String buildEmailBody(Employee employee) {
        return """
                An employee has requested a password reset.
                Please verify their identity and reset their password
                via the Employee Management panel.
                
                --- Employee Details ---
                ID       : %d
                Username : %s
                Full Name: %s
                Email    : %s
                Phone    : %s
                Role     : %s
                
                If you do not recognise this request, ignore this email.
                """.formatted(
                employee.getId(),
                employee.getUsername(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getRole().name()
        );
    }
}
