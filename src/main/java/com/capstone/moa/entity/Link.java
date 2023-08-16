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
    private String figma;
    private String erdCloud;
    private String projectLink;

    public Link(Group group) {
        this.group = group;
        this.figma = "Figma link";
        this.github = "Github link";
        this.erdCloud = "ErdCloud link";
        this.projectLink = "Project Link";
    }

    public void modifyGroupIntroLink(String githubLink, String projectLink) {
        this.github = githubLink;
        this.projectLink = projectLink;
    }

    public void modifyFigma(String figmaLink) {
        this.figma = figmaLink;
    }

    public void modifyErdCloud(String erdCloudLink) {
        this.erdCloud = erdCloudLink;
    }
}
