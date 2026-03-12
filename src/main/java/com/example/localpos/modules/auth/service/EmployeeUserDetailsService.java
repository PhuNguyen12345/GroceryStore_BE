package com.example.localpos.modules.auth.service;

import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found: " + username));

        return User.builder()
                .username(employee.getUsername())
                .password(employee.getPasswordHash())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + employee.getRole().name())))
                .accountLocked(!employee.getIsActive())
                .build();
    }
}
