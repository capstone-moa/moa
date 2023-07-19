package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    public Notice(Group group, String title, String content) {
        this.group = group;
        this.title = title;
        this.content = content;
    }
}
