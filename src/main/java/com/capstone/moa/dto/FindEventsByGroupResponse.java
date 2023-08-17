package com.capstone.moa.dto;

import com.capstone.moa.entity.Event;

import java.time.LocalDate;

public record FindEventsByGroupResponse(
        String title, LocalDate start, LocalDate end
) {
    public static FindEventsByGroupResponse from(Event event) {
        return new FindEventsByGroupResponse(
                event.getTitle(),
                event.getStart(),
                event.getEnd().plusDays(1)
        );
    }

}
