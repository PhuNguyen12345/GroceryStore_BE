package com.example.localpos.modules.hr.repository;

import com.example.localpos.modules.hr.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    Optional<Shift> findByName(String name);

    boolean existsByName(String name);

    // Find all active / inactive shifts
    List<Shift> findByIsActive(Boolean isActive);

    // Case-insensitive name search
    List<Shift> findByNameContainingIgnoreCase(String name);

    // Find shifts that start at or after a given time
    List<Shift> findByStartTimeGreaterThanEqual(LocalTime time);

    // Find shifts that end at or before a given time
    List<Shift> findByEndTimeLessThanEqual(LocalTime time);

    // Find shifts whose window overlaps a given time range
    @Query("""
            SELECT s FROM Shift s
            WHERE s.startTime < :endTime
              AND s.endTime   > :startTime
            """)
    List<Shift> findOverlapping(
            @Param("startTime") LocalTime startTime,
            @Param("endTime")   LocalTime endTime
    );

    // Find shifts that cover a specific point in time
    @Query("SELECT s FROM Shift s WHERE s.startTime <= :time AND s.endTime >= :time")
    List<Shift> findByTimeWithin(@Param("time") LocalTime time);

    long countByIsActive(Boolean isActive);
}
