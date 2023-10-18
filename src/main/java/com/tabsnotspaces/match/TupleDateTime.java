package com.tabsnotspaces.match;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class TupleDateTime {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public TupleDateTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}