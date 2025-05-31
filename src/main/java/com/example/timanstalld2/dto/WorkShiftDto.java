package com.example.timanstalld2.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkShiftDto {
    private Long id;
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private double totalHours;
    private int breakMinutes;
    private double baseHours;
    private double ob50Hours;
    private double ob70Hours;
    private double ob100Hours;
    private boolean weekend;
    private String note;
    private double salary;
}
