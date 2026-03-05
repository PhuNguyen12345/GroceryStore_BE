package com.example.localpos.modules.hr.service.impl;

import com.example.localpos.enums.EmployeeRole;
import com.example.localpos.modules.hr.dto.request.EmployeeRequestDTO;
import com.example.localpos.modules.hr.dto.response.EmployeeResponseDTO;
import com.example.localpos.modules.hr.dto.request.EmployeeUpdateDTO;
import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import com.example.localpos.modules.hr.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    private EmployeeResponseDTO toDTO(Employee employee) {
        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .role(employee.getRole())
                .isActive(employee.getIsActive())
                .createdAt(employee.getCreatedAt())
                .build();
    }

    // ── CRUD ─────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public EmployeeResponseDTO create(EmployeeRequestDTO request) {
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
        employee.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        employee.setCreatedAt(Instant.now());

        return toDTO(employeeRepository.save(employee));
    }

    /**
     * Partial update — only non-null fields in the DTO are applied.
     */
    @Override
    @Transactional
    public EmployeeResponseDTO update(Long id, EmployeeUpdateDTO request) {
        Employee employee = getEmployeeOrThrow(id);

        if (request.getUsername() != null) {
            if (!request.getUsername().equals(employee.getUsername())
                    && employeeRepository.existsByUsername(request.getUsername())) {
                throw new IllegalArgumentException("Username already exists: " + request.getUsername());
            }
            employee.setUsername(request.getUsername());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            employee.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getFullName() != null) {
            employee.setFullName(request.getFullName());
        }

        if (request.getEmail() != null) {
            if (!request.getEmail().equals(employee.getEmail())
                    && employeeRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + request.getEmail());
            }
            employee.setEmail(request.getEmail());
        }

        if (request.getPhone() != null) {
            employee.setPhone(request.getPhone());
        }

        if (request.getRole() != null) {
            employee.setRole(request.getRole());
        }

        if (request.getIsActive() != null) {
            employee.setIsActive(request.getIsActive());
        }

        return toDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // ── Single-record lookups ─────────────────────────────────────────────────

    @Override
    public EmployeeResponseDTO findById(Long id) {
        return toDTO(getEmployeeOrThrow(id));
    }

    @Override
    public EmployeeResponseDTO findByUsername(String username) {
        return employeeRepository.findByUsername(username)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with username: " + username));
    }

    @Override
    public EmployeeResponseDTO findByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with email: " + email));
    }

    // ── List / search ─────────────────────────────────────────────────────────

    @Override
    public List<EmployeeResponseDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EmployeeResponseDTO> search(String keyword, EmployeeRole role, Boolean isActive, Pageable pageable) {
        return employeeRepository
                .searchEmployees(keyword, role, isActive, pageable)
                .map(this::toDTO);
    }

    @Override
    public List<EmployeeResponseDTO> findByFullName(String fullName) {
        return employeeRepository.findByFullNameContainingIgnoreCase(fullName)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDTO> findByRole(EmployeeRole role) {
        return employeeRepository.findByRole(role)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDTO> findByIsActive(Boolean isActive) {
        return employeeRepository.findByIsActive(isActive)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ── Status helpers ────────────────────────────────────────────────────────

    @Override
    @Transactional
    public EmployeeResponseDTO activate(Long id) {
        Employee employee = getEmployeeOrThrow(id);
        employee.setIsActive(true);
        return toDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO deactivate(Long id) {
        Employee employee = getEmployeeOrThrow(id);
        employee.setIsActive(false);
        return toDTO(employeeRepository.save(employee));
    }

    // ── Statistics ────────────────────────────────────────────────────────────

    @Override
    public long countByRole(EmployeeRole role) {
        return employeeRepository.countByRole(role);
    }

    @Override
    public long countActive() {
        return employeeRepository.countByIsActive(true);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private Employee getEmployeeOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }
}
