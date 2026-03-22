package com.example.localpos.config;

import com.example.localpos.enums.EmployeeRole;
import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class EmployeeDataLoader {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedEmployees() {
        return args -> {
            List<EmployeeSeed> seeds = List.of(
                    new EmployeeSeed("admin", "123", "System Admin", "admin@localpos.dev", "0900000001", EmployeeRole.ADMIN),
                    new EmployeeSeed("manager", "123", "Store Manager", "manager@localpos.dev", "0900000002", EmployeeRole.STORE_MANAGER),
                    new EmployeeSeed("inventory", "123", "Inventory Staff", "inventory@localpos.dev", "0900000003", EmployeeRole.INVENTORY_STAFF),
                    new EmployeeSeed("cashier", "123", "Main Cashier", "cashier@localpos.dev", "0900000004", EmployeeRole.CASHIER)
            );

            for (EmployeeSeed seed : seeds) {
                if (employeeRepository.existsByUsername(seed.username())) {
                    continue;
                }

                Employee employee = new Employee();
                employee.setUsername(seed.username());
                employee.setPasswordHash(passwordEncoder.encode(seed.rawPassword()));
                employee.setFullName(seed.fullName());
                employee.setEmail(seed.email());
                employee.setPhone(seed.phone());
                employee.setRole(seed.role());
                employee.setIsActive(true);
                employee.setCreatedAt(Instant.now());

                employeeRepository.save(employee);
            }
        };
    }

    private record EmployeeSeed(
            String username,
            String rawPassword,
            String fullName,
            String email,
            String phone,
            EmployeeRole role
    ) {}
}
