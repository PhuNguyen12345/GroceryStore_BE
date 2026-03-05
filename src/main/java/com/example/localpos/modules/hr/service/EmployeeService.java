package com.example.localpos.modules.hr.service;

import com.example.localpos.enums.EmployeeRole;
import com.example.localpos.modules.hr.dto.request.EmployeeRequestDTO;
import com.example.localpos.modules.hr.dto.response.EmployeeResponseDTO;
import com.example.localpos.modules.hr.dto.request.EmployeeUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    // ── CRUD ────────────────────────────────────────────────────────────────

    EmployeeResponseDTO create(EmployeeRequestDTO request);

    EmployeeResponseDTO update(Long id, EmployeeUpdateDTO request);

    void delete(Long id);

    // ── Single-record lookups ────────────────────────────────────────────────

    EmployeeResponseDTO findById(Long id);

    EmployeeResponseDTO findByUsername(String username);

    EmployeeResponseDTO findByEmail(String email);

    // ── List / search ────────────────────────────────────────────────────────

    List<EmployeeResponseDTO> findAll();

    /**
     * Paginated, filterable search.
     *
     * @param keyword  optional free-text filter (username / fullName / email)
     * @param role     optional role filter
     * @param isActive optional active-status filter
     * @param pageable pagination & sorting info
     */
    Page<EmployeeResponseDTO> search(String keyword, EmployeeRole role, Boolean isActive, Pageable pageable);

    List<EmployeeResponseDTO> findByFullName(String fullName);

    List<EmployeeResponseDTO> findByRole(EmployeeRole role);

    List<EmployeeResponseDTO> findByIsActive(Boolean isActive);

    // ── Status helpers ───────────────────────────────────────────────────────

    EmployeeResponseDTO activate(Long id);

    EmployeeResponseDTO deactivate(Long id);

    // ── Statistics ───────────────────────────────────────────────────────────

    long countByRole(EmployeeRole role);

    long countActive();
}
