package com.example.localpos.modules.hr.service;

import com.example.localpos.modules.hr.dto.request.ShiftRequestDTO;
import com.example.localpos.modules.hr.dto.response.ShiftResponseDTO;
import com.example.localpos.modules.hr.dto.request.ShiftUpdateDTO;

import java.time.LocalTime;
import java.util.List;

public interface ShiftService {

    //  CRUD

    ShiftResponseDTO create(ShiftRequestDTO request);

    ShiftResponseDTO update(Long id, ShiftUpdateDTO request);

    void delete(Long id);

    // Single-record lookups

    ShiftResponseDTO findById(Long id);

    ShiftResponseDTO findByName(String name);

    // List / search

    List<ShiftResponseDTO> findAll();

    List<ShiftResponseDTO> findByIsActive(Boolean isActive);

    List<ShiftResponseDTO> searchByName(String keyword);

    // Time-based lookups

    /**
     * Returns shifts that start at or after {@code time}.
     */
    List<ShiftResponseDTO> findStartingFrom(LocalTime time);

    /**
     * Returns shifts that end at or before {@code time}.
     */
    List<ShiftResponseDTO> findEndingBefore(LocalTime time);

    /**
     * Returns shifts whose window overlaps the given [startTime, endTime] range.
     */
    List<ShiftResponseDTO> findOverlapping(LocalTime startTime, LocalTime endTime);

    /**
     * Returns shifts that are active at the given point in time.
     */
    List<ShiftResponseDTO> findActiveAt(LocalTime time);

    // Status helpers

    ShiftResponseDTO activate(Long id);

    ShiftResponseDTO deactivate(Long id);

    // Statistics

    long countActive();
}
