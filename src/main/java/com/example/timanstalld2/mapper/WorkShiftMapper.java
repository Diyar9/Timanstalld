package com.example.timanstalld2.mapper;

import com.example.timanstalld2.dto.WorkShiftDto;
import com.example.timanstalld2.entity.WorkShift;

public class WorkShiftMapper {

    public static WorkShiftDto mapToWorkShiftDto(WorkShift workShift) {
        return new WorkShiftDto(
                workShift.getId(),
                workShift.getWorkDate(),
                workShift.getStartTime(),
                workShift.getEndTime(),
                workShift.getTotalHours(),
                workShift.getBreakMinutes(),
                workShift.getBaseHours(),
                workShift.getOb50Hours(),
                workShift.getOb70Hours(),
                workShift.getOb100Hours(),
                workShift.isWeekend(),
                workShift.getNote(),
                workShift.getSalary()
        );
    }

    public static WorkShift mapToWorkShift(WorkShiftDto workShiftDto) {
        return new WorkShift(
                workShiftDto.getId(),
                workShiftDto.getWorkDate(),
                workShiftDto.getStartTime(),
                workShiftDto.getEndTime(),
                workShiftDto.getTotalHours(),
                workShiftDto.getBreakMinutes(),
                workShiftDto.getBaseHours(),
                workShiftDto.getOb50Hours(),
                workShiftDto.getOb70Hours(),
                workShiftDto.getOb100Hours(),
                workShiftDto.isWeekend(),
                workShiftDto.getNote(),
                workShiftDto.getSalary()
        );
    }
}
