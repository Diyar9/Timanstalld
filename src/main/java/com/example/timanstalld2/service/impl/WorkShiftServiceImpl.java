package com.example.timanstalld2.service.impl;

import com.example.timanstalld2.dto.WorkShiftDto;
import com.example.timanstalld2.entity.WorkShift;
import com.example.timanstalld2.exception.ResourceNotFoundException;
import com.example.timanstalld2.mapper.WorkShiftMapper;
import com.example.timanstalld2.repository.WorkShiftRepository;
import com.example.timanstalld2.service.WorkShiftService;
import com.example.timanstalld2.util.OBPeriod;
import lombok.AllArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkShiftServiceImpl implements WorkShiftService {

    private WorkShiftRepository workShiftRepository;

    @Override
    public WorkShiftDto createWorkShift(WorkShiftDto workShiftDto) {
        WorkShift workShift = WorkShiftMapper.mapToWorkShift(workShiftDto);

        double hourlyWage = 158.38;

        // Skapa OB-perioder för detta arbetspass
        List<OBPeriod> obPeriods = getObPeriodsForDate(workShiftDto);

        // Skicka med OB-perioderna
        double totalSalary = calculateSalary(workShiftDto, hourlyWage, obPeriods);

        workShift.setSalary(totalSalary);
        WorkShift savedWorkShift = workShiftRepository.save(workShift);
        return WorkShiftMapper.mapToWorkShiftDto(savedWorkShift);
    }

    @Override
    public WorkShiftDto getWorkShiftById(Long workShiftId) {
        WorkShift workShift = workShiftRepository.findById(workShiftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Work shift not found with given id: " + workShiftId));
        return WorkShiftMapper.mapToWorkShiftDto(workShift);
    }

    @Override
    public List<WorkShiftDto> getAllWorkShifts() {
        List<WorkShift> workShifts = workShiftRepository.findAll(Sort.by(Sort.Direction.ASC, "workDate", "startTime"));
        return workShifts.stream().map((workShift) -> WorkShiftMapper.mapToWorkShiftDto(workShift))
                .collect(Collectors.toList());
    }

    @Override
    public WorkShiftDto updateWorkShift(Long workShiftId, WorkShiftDto updatedWorkShift) {

        WorkShift workShift = workShiftRepository.findById(workShiftId).orElseThrow(
                () -> new ResourceNotFoundException("Work shift not found with given id: " + workShiftId)
        );

        workShift.setWorkDate(updatedWorkShift.getWorkDate());
        workShift.setStartTime(updatedWorkShift.getStartTime());
        workShift.setEndTime(updatedWorkShift.getEndTime());
        workShift.setBreakMinutes(updatedWorkShift.getBreakMinutes());
        //workShift.setWeekend(updatedWorkShift.isWeekend());
        workShift.setNote(updatedWorkShift.getNote());


        double hourlyWage = 158.38;
        List<OBPeriod> obPeriods = getObPeriodsForDate(updatedWorkShift);
        double totalSalary = calculateSalary(updatedWorkShift, hourlyWage, obPeriods);
        workShift.setSalary(totalSalary);

        WorkShift updatedWorkShiftObj = workShiftRepository.save(workShift);

        return WorkShiftMapper.mapToWorkShiftDto(updatedWorkShiftObj);
    }


    @Override
    public void deleteWorkShift(Long workShiftId) {

        WorkShift workShift = workShiftRepository.findById(workShiftId).orElseThrow(
                () -> new ResourceNotFoundException("Work shift not found with given id: " + workShiftId)
        );

        workShiftRepository.deleteById(workShiftId);
    }

    @Override
    public double getTotalSalary() {
        List<WorkShift> workShifts = workShiftRepository.findAll();

        return workShifts.stream()
                .mapToDouble(WorkShift::getSalary)
                .sum();
    }

    public double calculateSalary(WorkShiftDto workShiftDto, double hourlyWage, List<OBPeriod> obPeriods) {
        LocalTime start = workShiftDto.getStartTime();
        LocalTime end = workShiftDto.getEndTime();
        int breakMinutes = workShiftDto.getBreakMinutes();

        /*int ob50Minutes = 0;
        int ob70Minutes = 0;
        int ob100Minutes = 0;*/

        long totalMinutes = java.time.Duration.between(start, end).toMinutes() - breakMinutes;
        double totalSalary = 0.0;

        while (start.isBefore(end) && totalMinutes > 0) {
            LocalTime currentTime = start;
            double factor = 1.0;

            // Kolla om aktuell minut ligger inom en OB-period
            for (OBPeriod ob : obPeriods) {
                if (ob.isWithin(currentTime)) {
                    factor = ob.getFactor();
                    /*if (factor == 1.5) ob50Minutes++;
                    else if (factor == 1.7) ob70Minutes++;
                    else if (factor == 2.0) ob100Minutes++;*/
                    break;
                }
            }

            // Lägg till lön för denna minut, justerat med OB-faktorn
            totalSalary += (hourlyWage / 60.0) * factor;

            // Gå vidare till nästa minut
            start = start.plusMinutes(1);
            totalMinutes--;
        }

        /*double ob50Hours = ob50Minutes / 60.0;
        double ob70Hours = ob70Minutes / 60.0;
        double ob100Hours = ob100Minutes / 60.0;*/

        return totalSalary;
    }

    // Hjälpmetoder för att kontrollera veckodag
    private boolean isWeekday(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 1 && date.getDayOfWeek().getValue() <= 5; // Måndag till fredag
    }

    private boolean isSaturday(LocalDate date) {
        return date.getDayOfWeek().getValue() == 6; // Lördag
    }

    private boolean isSunday(LocalDate date) {
        return date.getDayOfWeek().getValue() == 7; // Söndag
    }

    private List<OBPeriod> getObPeriodsForDate(WorkShiftDto workShiftDto) {
        LocalDate date = workShiftDto.getWorkDate();

        if (isSunday(date)) {
            // Hela dagen OB100%
            return List.of(new OBPeriod(LocalTime.MIN, LocalTime.MAX, 2.0));
        } else if (isSaturday(date)) {
            // OB100% från 12:00
            return List.of(new OBPeriod(LocalTime.of(12, 0), LocalTime.MAX, 2.0));
        } else if (isWeekday(date)) {
            // OB70% från 20:00, OB50% från 18:15 till 20:00
            return List.of(
                    new OBPeriod(LocalTime.of(20, 0), LocalTime.MAX, 1.7),
                    new OBPeriod(LocalTime.of(18, 15), LocalTime.of(20, 0), 1.5)
            );
        }

        return List.of(); // Om inget passar, ingen OB
    }
}
