package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public void modifyGithub(String githubLink) {
        this.github = githubLink;
    }

    public void modifyFigma(String figmaLink) {
        this.figma = figmaLink;
    }

    public void modifyErdCloud(String erdCloudLink) {
        this.erdCloud = erdCloudLink;
    }
}
