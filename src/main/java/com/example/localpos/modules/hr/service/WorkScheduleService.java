package com.example.localpos.modules.hr.service;

import com.example.localpos.modules.hr.dto.request.WorkScheduleRequestDTO;
import com.example.localpos.modules.hr.dto.response.WorkScheduleResponseDTO;
import com.example.localpos.modules.hr.dto.request.WorkScheduleUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface WorkScheduleService {

    // CRUD

    WorkScheduleResponseDTO create(WorkScheduleRequestDTO request);

    WorkScheduleResponseDTO update(Long id, WorkScheduleUpdateDTO request);

    void delete(Long id);

    // Single-record lookups

    WorkScheduleResponseDTO findById(Long id);

    WorkScheduleResponseDTO findByEmployeeAndDate(Long employeeId, LocalDate date);

    // List / search

    List<WorkScheduleResponseDTO> findAll();

    /**
     * Paginated, filterable search across all key dimensions.
     */
    Page<WorkScheduleResponseDTO> search(
            Long employeeId,
            Long shiftId,
            LocalDate from,
            LocalDate to,
            Boolean isPresent,
            Pageable pageable
    );

    // By employee

    List<WorkScheduleResponseDTO> findByEmployee(Long employeeId);

    List<WorkScheduleResponseDTO> findByEmployeeAndDateRange(Long employeeId, LocalDate from, LocalDate to);

    List<WorkScheduleResponseDTO> findByEmployeeAndAttendance(Long employeeId, Boolean isPresent);

    // By shift

    List<WorkScheduleResponseDTO> findByShift(Long shiftId);

    List<WorkScheduleResponseDTO> findByShiftAndDate(Long shiftId, LocalDate date);

    // By date

    List<WorkScheduleResponseDTO> findByDate(LocalDate date);

    List<WorkScheduleResponseDTO> findByDateRange(LocalDate from, LocalDate to);

    // Attendance

    List<WorkScheduleResponseDTO> findByAttendance(Boolean isPresent);

    List<WorkScheduleResponseDTO> findByDateAndAttendance(LocalDate date, Boolean isPresent);

    /**
     * Record check-in for a schedule entry.
     */
    WorkScheduleResponseDTO checkIn(Long id, Instant checkInTime);

    /**
     * Record check-out for a schedule entry.
     */
    WorkScheduleResponseDTO checkOut(Long id, Instant checkOutTime);

    /**
     * Mark a schedule entry as present (isPresent = true).
     */
    WorkScheduleResponseDTO markPresent(Long id);

    /**
     * Mark a schedule entry as absent (isPresent = false).
     */
    WorkScheduleResponseDTO markAbsent(Long id);

    // Statistics

    long countByDate(LocalDate date);

    long countPresentByDate(LocalDate date);

    long countAbsentByDate(LocalDate date);

    /**
     * Total days an employee was scheduled in a date range.
     */
    long countScheduledDays(Long employeeId, LocalDate from, LocalDate to);

    /**
     * Total days an employee was present in a date range.
     */
    long countAttendedDays(Long employeeId, LocalDate from, LocalDate to);
}
