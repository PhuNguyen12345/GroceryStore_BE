package com.example.localpos.modules.hr.service.impl;

import com.example.localpos.modules.hr.dto.request.ShiftRequestDTO;
import com.example.localpos.modules.hr.dto.response.ShiftResponseDTO;
import com.example.localpos.modules.hr.dto.request.ShiftUpdateDTO;
import com.example.localpos.modules.hr.entity.Shift;
import com.example.localpos.modules.hr.repository.ShiftRepository;
import com.example.localpos.modules.hr.service.ShiftService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;

    private ShiftResponseDTO toDTO(Shift shift) {
        return ShiftResponseDTO.builder()
                .id(shift.getId())
                .name(shift.getName())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .description(shift.getDescription())
                .isActive(shift.getIsActive())
                .build();
    }

    private Shift getShiftOrThrow(Long id) {
        return shiftRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shift not found with id: " + id));
    }

    private void validateTimeRange(LocalTime start, LocalTime end) {
        if (start != null && end != null && !end.isAfter(start)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }

    @Override
    @Transactional
    public ShiftResponseDTO create(ShiftRequestDTO request) {
        if (shiftRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Shift name already exists: " + request.getName());
        }
        validateTimeRange(request.getStartTime(), request.getEndTime());

        Shift shift = new Shift();
        shift.setName(request.getName());
        shift.setStartTime(request.getStartTime());
        shift.setEndTime(request.getEndTime());
        shift.setDescription(request.getDescription());
        shift.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        return toDTO(shiftRepository.save(shift));
    }

    @Override
    @Transactional
    public ShiftResponseDTO update(Long id, ShiftUpdateDTO request) {
        Shift shift = getShiftOrThrow(id);

        if (request.getName() != null) {
            if (!request.getName().equals(shift.getName())
                    && shiftRepository.existsByName(request.getName())) {
                throw new IllegalArgumentException("Shift name already exists: " + request.getName());
            }
            shift.setName(request.getName());
        }

        // Resolve effective times for validation (fall back to existing values)
        LocalTime effectiveStart = request.getStartTime() != null ? request.getStartTime() : shift.getStartTime();
        LocalTime effectiveEnd   = request.getEndTime()   != null ? request.getEndTime()   : shift.getEndTime();
        validateTimeRange(effectiveStart, effectiveEnd);

        if (request.getStartTime() != null) {
            shift.setStartTime(request.getStartTime());
        }

        if (request.getEndTime() != null) {
            shift.setEndTime(request.getEndTime());
        }

        if (request.getDescription() != null) {
            shift.setDescription(request.getDescription());
        }

        if (request.getIsActive() != null) {
            shift.setIsActive(request.getIsActive());
        }

        return toDTO(shiftRepository.save(shift));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!shiftRepository.existsById(id)) {
            throw new EntityNotFoundException("Shift not found with id: " + id);
        }
        shiftRepository.deleteById(id);
    }

    @Override
    public ShiftResponseDTO findById(Long id) {
        return toDTO(getShiftOrThrow(id));
    }

    @Override
    public ShiftResponseDTO findByName(String name) {
        return shiftRepository.findByName(name)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Shift not found with name: " + name));
    }

    @Override
    public List<ShiftResponseDTO> findAll() {
        return shiftRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftResponseDTO> findByIsActive(Boolean isActive) {
        return shiftRepository.findByIsActive(isActive)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftResponseDTO> searchByName(String keyword) {
        return shiftRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftResponseDTO> findStartingFrom(LocalTime time) {
        return shiftRepository.findByStartTimeGreaterThanEqual(time)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftResponseDTO> findEndingBefore(LocalTime time) {
        return shiftRepository.findByEndTimeLessThanEqual(time)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftResponseDTO> findOverlapping(LocalTime startTime, LocalTime endTime) {
        validateTimeRange(startTime, endTime);
        return shiftRepository.findOverlapping(startTime, endTime)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftResponseDTO> findActiveAt(LocalTime time) {
        return shiftRepository.findByTimeWithin(time)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShiftResponseDTO activate(Long id) {
        Shift shift = getShiftOrThrow(id);
        shift.setIsActive(true);
        return toDTO(shiftRepository.save(shift));
    }

    @Override
    @Transactional
    public ShiftResponseDTO deactivate(Long id) {
        Shift shift = getShiftOrThrow(id);
        shift.setIsActive(false);
        return toDTO(shiftRepository.save(shift));
    }

    @Override
    public long countActive() {
        return shiftRepository.countByIsActive(true);
    }

}
