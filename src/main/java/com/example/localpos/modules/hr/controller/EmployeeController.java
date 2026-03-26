package com.example.localpos.modules.hr.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.enums.EmployeeRole;
import com.example.localpos.modules.hr.dto.request.EmployeeRequestDTO;
import com.example.localpos.modules.hr.dto.request.ResetPasswordRequest;
import com.example.localpos.modules.hr.dto.response.EmployeeResponseDTO;
import com.example.localpos.modules.hr.dto.request.EmployeeUpdateDTO;
import com.example.localpos.modules.hr.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ApiPaths.HRCtrl.EMPLOYEE)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('STORE_MANAGER', 'ADMIN')")
public class EmployeeController {

    private final EmployeeService employeeService;

    //=========================== For manipulating data ==========================//
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create(@Valid @RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.create(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateDTO request) {
        return ResponseEntity.ok(employeeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

 //=========================== For listing, getting entity ==========================//
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<EmployeeResponseDTO> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(employeeService.findByUsername(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeResponseDTO> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(employeeService.findByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeResponseDTO>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) EmployeeRole role,
            @RequestParam(required = false) Boolean isActive,
            @PageableDefault(size = 20, sort = "fullName") Pageable pageable) {
        return ResponseEntity.ok(employeeService.search(keyword, role, isActive, pageable));
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<EmployeeResponseDTO>> findByFullName(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.findByFullName(name));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<EmployeeResponseDTO>> findByRole(@PathVariable EmployeeRole role) {
        return ResponseEntity.ok(employeeService.findByRole(role));
    }


    @GetMapping("/active")
    public ResponseEntity<List<EmployeeResponseDTO>> findByActiveStatus(
            @RequestParam(defaultValue = "true") Boolean status) {
        return ResponseEntity.ok(employeeService.findByIsActive(status));
    }

    //=========================== For activating/deactivating ==========================//
    @PatchMapping("/{id}/activate")
    public ResponseEntity<EmployeeResponseDTO> activate(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.activate(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<EmployeeResponseDTO> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.deactivate(id));
    }

    //=========================== For counting records ==========================//
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> stats() {
        long totalActive = employeeService.countActive();

        Map<String, Long> byRole = new java.util.LinkedHashMap<>();
        for (EmployeeRole role : EmployeeRole.values()) {
            byRole.put(role.name(), employeeService.countByRole(role));
        }
        return ResponseEntity.ok(Map.of(
                "totalActive", totalActive,
                "countByRole", byRole
        ));
    }

    @PatchMapping("/{id}/reset-password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> resetPassword(
            @PathVariable Long id,
            @RequestBody @Valid ResetPasswordRequest req
    ) {
        employeeService.resetPassword(id, req.newPassword());
        return ResponseEntity.ok().build();
    }
}
