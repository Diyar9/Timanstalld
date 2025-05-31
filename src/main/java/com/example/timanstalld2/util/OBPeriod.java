package com.example.timanstalld2.util;

import java.time.LocalTime;

public class OBPeriod {
    private final LocalTime start;
    private final LocalTime end;
    private final double factor;

    public OBPeriod(LocalTime start, LocalTime end, double factor) {
        this.start = start;
        this.end = end;
        this.factor = factor;
    }

    public boolean isWithin(LocalTime time) {
        // Hanterar både vanliga och över-midnatt perioder (t.ex. 22:00 - 02:00)
        if (end.isAfter(start)) {
            return !time.isBefore(start) && time.isBefore(end);
        } else {
            // Period sträcker sig över midnatt
            return !time.isBefore(start) || time.isBefore(end);
        }
    }

    public double getFactor() {
        return factor;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
