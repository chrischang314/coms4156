package com.tabsnotspaces.match;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

/**
 * A class representing a tuple of LocalDateTime values, typically used for
 * specifying a start and end time for an event.
 */
@Embeddable
public class TupleDateTime {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    
    public TupleDateTime() {
        super();
        this.startTime = null;
        this.endTime = null;
    }

    /**
     * Constructs a TupleDateTime object with a start time and an end time.
     *
     * @param startTime The start time of the event.
     * @param endTime   The end time of the event.
     * @throws IllegalArgumentException If the start time is after the end time.
     */
    public TupleDateTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Get the start time of the TupleDateTime.
     *
     * @return The start time.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Get the end time of the TupleDateTime.
     *
     * @return The end time.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }
}