package com.example.timanstalld2.service;

import com.example.timanstalld2.dto.WorkShiftDto;
import com.example.timanstalld2.entity.WorkShift;

import java.util.List;

public interface WorkShiftService {
    WorkShiftDto createWorkShift(WorkShiftDto workShiftDto);

    WorkShiftDto getWorkShiftById(Long workShiftId);

    List<WorkShiftDto> getAllWorkShifts();

    WorkShiftDto updateWorkShift(Long workShiftId, WorkShiftDto updatedWorkShift);

    void deleteWorkShift(Long workShiftId);

    double getTotalSalary();
}
