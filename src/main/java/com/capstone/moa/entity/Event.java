package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private String title;
    private LocalDate start;
    private LocalDate end;

    @Builder
    public Event(Group group, String title, LocalDate start, LocalDate end) {
        this.group = group;
        this.title = title;
        this.start = start;
        this.end = end;
    }
}
