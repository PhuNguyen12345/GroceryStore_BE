package com.example.localpos.modules.hr.repository;

import com.example.localpos.modules.hr.entity.WorkSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {

    // Existence checks

    boolean existsByEmployeeIdAndShiftIdAndWorkDate(Long employeeId, Long shiftId, LocalDate workDate);

    // Single-record lookups

    Optional<WorkSchedule> findByEmployeeIdAndWorkDate(Long employeeId, LocalDate workDate);

    // By employee

    List<WorkSchedule> findByEmployeeId(Long employeeId);

    List<WorkSchedule> findByEmployeeIdAndWorkDateBetween(Long employeeId, LocalDate from, LocalDate to);

    List<WorkSchedule> findByEmployeeIdAndIsPresent(Long employeeId, Boolean isPresent);

    // By shift

    List<WorkSchedule> findByShiftId(Long shiftId);

    List<WorkSchedule> findByShiftIdAndWorkDate(Long shiftId, LocalDate workDate);

    // By date / date range

    List<WorkSchedule> findByWorkDate(LocalDate workDate);

    List<WorkSchedule> findByWorkDateBetween(LocalDate from, LocalDate to);

    // By attendance

    List<WorkSchedule> findByIsPresent(Boolean isPresent);

    List<WorkSchedule> findByWorkDateAndIsPresent(LocalDate workDate, Boolean isPresent);

    // Paginated search

    @Query("""
            SELECT ws FROM WorkSchedule ws
            WHERE (:employeeId IS NULL OR ws.employee.id = :employeeId)
              AND (:shiftId    IS NULL OR ws.shift.id    = :shiftId)
              AND (:from       IS NULL OR ws.workDate   >= :from)
              AND (:to         IS NULL OR ws.workDate   <= :to)
              AND (:isPresent  IS NULL OR ws.isPresent   = :isPresent)
            """)
    Page<WorkSchedule> search(
            @Param("employeeId") Long employeeId,
            @Param("shiftId")    Long shiftId,
            @Param("from")       LocalDate from,
            @Param("to")         LocalDate to,
            @Param("isPresent")  Boolean isPresent,
            Pageable pageable
    );

    // Statistics

    long countByWorkDate(LocalDate workDate);

    long countByWorkDateAndIsPresent(LocalDate workDate, Boolean isPresent);

    long countByEmployeeIdAndWorkDateBetweenAndIsPresent(Long employeeId, LocalDate from, LocalDate to, Boolean isPresent);

    @Query("""
            SELECT COUNT(ws) FROM WorkSchedule ws
            WHERE ws.employee.id = :employeeId
              AND ws.workDate BETWEEN :from AND :to
            """)
    long countScheduledDays(
            @Param("employeeId") Long employeeId,
            @Param("from")       LocalDate from,
            @Param("to")         LocalDate to
    );
}
