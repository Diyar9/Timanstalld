package com.example.timanstalld2.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_shift")
public class WorkShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_date")
    private LocalDate workDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "total_hours")
    private double totalHours;

    @Column(name = "break_minutes")
    private int breakMinutes;

    @Column(name = "base_hours")
    private double baseHours;

    @Column(name = "ob50_hours")
    private double ob50Hours;

    @Column(name = "ob70_hours")
    private double ob70Hours;

    @Column(name = "ob100_hours")
    private double ob100Hours;

    @Column(name = "is_weekend")
    private boolean weekend;

    @Column(name = "note")
    private String note;

    @Column(name = "salary")
    private Double salary;
}

