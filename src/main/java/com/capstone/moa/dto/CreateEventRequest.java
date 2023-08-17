package com.capstone.moa.dto;

import com.capstone.moa.entity.Event;
import com.capstone.moa.entity.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CreateEventRequest {

    private Long groupId;
    private String title;
    private LocalDate start;
    private LocalDate end;

    public Event toEntity(Group group) {
        return Event.builder()
                .group(group)
                .title(title)
                .start(start)
                .end(end)
                .build();
    }
}
