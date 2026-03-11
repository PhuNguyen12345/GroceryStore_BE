package com.example.localpos.modules.hr.controller;

import com.example.localpos.modules.hr.dto.request.ShiftRequestDTO;
import com.example.localpos.modules.hr.dto.response.ShiftResponseDTO;
import com.example.localpos.modules.hr.dto.request.ShiftUpdateDTO;
import com.example.localpos.modules.hr.service.ShiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/shifts")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    // CRUD

    /**
     * POST /api/v1/shifts
     */
    @PostMapping
    public ResponseEntity<ShiftResponseDTO> create(@Valid @RequestBody ShiftRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(shiftService.create(request));
    }

    /**
     * PATCH /api/v1/shifts/{id}
     * Partially update a shift — only supplied fields are modified.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ShiftResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ShiftUpdateDTO request) {
        return ResponseEntity.ok(shiftService.update(id, request));
    }

    /**
     * DELETE /api/v1/shifts/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        shiftService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Single-record lookups

    /**
     * GET /api/v1/shifts/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShiftResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(shiftService.findById(id));
    }

    /**
     * GET /api/v1/shifts/name/{name}
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<ShiftResponseDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok(shiftService.findByName(name));
    }

    // List / search

    /**
     * GET /api/v1/shifts
     */
    @GetMapping
    public ResponseEntity<List<ShiftResponseDTO>> findAll() {
        return ResponseEntity.ok(shiftService.findAll());
    }

    /**
     * GET /api/v1/shifts/active?status=true|false
     */
    @GetMapping("/active")
    public ResponseEntity<List<ShiftResponseDTO>> findByActiveStatus(
            @RequestParam(defaultValue = "true") Boolean status) {
        return ResponseEntity.ok(shiftService.findByIsActive(status));
    }

    /**
     * GET /api/v1/shifts/search?keyword=
     */
    @GetMapping("/search")
    public ResponseEntity<List<ShiftResponseDTO>> searchByName(@RequestParam String keyword) {
        return ResponseEntity.ok(shiftService.searchByName(keyword));
    }

    // Time-based lookups

    /**
     * GET /api/v1/shifts/starting-from?time=HH:mm
     */
    @GetMapping("/starting-from")
    public ResponseEntity<List<ShiftResponseDTO>> findStartingFrom(
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime time) {
        return ResponseEntity.ok(shiftService.findStartingFrom(time));
    }

    /**
     * GET /api/v1/shifts/ending-before?time=HH:mm
     */
    @GetMapping("/ending-before")
    public ResponseEntity<List<ShiftResponseDTO>> findEndingBefore(
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime time) {
        return ResponseEntity.ok(shiftService.findEndingBefore(time));
    }

    /**
     * GET /api/v1/shifts/overlapping?startTime=HH:mm&endTime=HH:mm
     * Useful for conflict checking before assigning a shift.
     */
    @GetMapping("/overlapping")
    public ResponseEntity<List<ShiftResponseDTO>> findOverlapping(
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime endTime) {
        return ResponseEntity.ok(shiftService.findOverlapping(startTime, endTime));
    }

    /**
     * GET /api/v1/shifts/active-at?time=HH:mm
     * Returns all shifts currently running at the given time.
     */
    @GetMapping("/active-at")
    public ResponseEntity<List<ShiftResponseDTO>> findActiveAt(
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime time) {
        return ResponseEntity.ok(shiftService.findActiveAt(time));
    }

    // Status helpers

    /**
     * PATCH /api/v1/shifts/{id}/activate
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ShiftResponseDTO> activate(@PathVariable Long id) {
        return ResponseEntity.ok(shiftService.activate(id));
    }

    /**
     * PATCH /api/v1/shifts/{id}/deactivate
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ShiftResponseDTO> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(shiftService.deactivate(id));
    }

    // Statistics

    /**
     * GET /api/v1/shifts/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> stats() {
        return ResponseEntity.ok(Map.of(
                "totalActive", shiftService.countActive()
        ));
    }
}
