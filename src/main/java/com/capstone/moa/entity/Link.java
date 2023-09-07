package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;
    private String github;
    private String projectLink;

    public Link(Group group) {
        this.group = group;
        this.github = "Github link";
        this.projectLink = "Project Link";
    }

    public void modifyGroupIntroLink(String githubLink, String projectLink) {
        this.github = githubLink;
        this.projectLink = projectLink;
    }
}
