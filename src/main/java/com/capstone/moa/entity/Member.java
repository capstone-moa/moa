package com.capstone.moa.entity;

import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.entity.enums.Job;
import com.capstone.moa.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String github;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Interest interest;

    @Enumerated(EnumType.STRING)
    private Job job;

    private String introduce;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<GroupMember> groupMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> invitations = new ArrayList<>();

    @Builder
    public Member(Long id, String name, String email, String password, String github, Role role, Interest interest, Job job) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.github = github;
        this.role = role;
        this.interest = interest;
        this.job = job;
    }

    public void modify(String name, Job job, Interest interest, String github, String introduce) {
        this.name = name;
        this.job = job;
        this.interest = interest;
        this.github = github;
        this.introduce = introduce;
    }
}
