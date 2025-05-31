package com.example.timanstalld2.controller;

import com.example.timanstalld2.dto.WorkShiftDto;
import com.example.timanstalld2.service.WorkShiftService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/workshift")
public class WorkShiftController {

    private WorkShiftService workShiftService;

    // Build Add WorkShift REST API
    @PostMapping
    public ResponseEntity<WorkShiftDto> createWorkShift(@RequestBody WorkShiftDto workShiftDto) {
        WorkShiftDto savedWorkShift = workShiftService.createWorkShift(workShiftDto);
        return new ResponseEntity<>(savedWorkShift, HttpStatus.CREATED);
    }

    // Build Get WorkShift REST API
    @GetMapping("{id}")
    public ResponseEntity<WorkShiftDto> getWorkShiftById(@PathVariable("id") Long workShiftId) {
        WorkShiftDto workShiftDto = workShiftService.getWorkShiftById(workShiftId);
        return ResponseEntity.ok(workShiftDto);
    }

    // Build Get All WorkShift REST API
    @GetMapping
    public ResponseEntity<List<WorkShiftDto>> getAllWorkShifts() {
        List<WorkShiftDto> workShift = workShiftService.getAllWorkShifts();
        return ResponseEntity.ok(workShift);
    }

    // Build Update WorkShift REST API
    @PutMapping("{id}")
    public ResponseEntity<WorkShiftDto> updateWorkShift(@PathVariable("id") Long workShiftId,
                                                        @RequestBody WorkShiftDto updatedWorkShift) {
        WorkShiftDto workShiftDto = workShiftService.updateWorkShift(workShiftId, updatedWorkShift);
        return ResponseEntity.ok(workShiftDto);
    }

    // Build Delete WorkShift REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteWorkShift(@PathVariable("id") Long workShiftId) {
        workShiftService.deleteWorkShift(workShiftId);
        return ResponseEntity.ok("Work shift with id: " + workShiftId + " deleted successfully!");
    }

    @GetMapping("/salary/total")
    public double getTotalSalary() {
        return workShiftService.getTotalSalary();
    }
}
