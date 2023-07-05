package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "\"group\"")
public class Group extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Interest interest;

    @Lob
    private String introduce;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupMember> groupMembers = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Invitation> invitations = new ArrayList<>();

    public Group(String name, String interest, String introduce) {
        this(name, Interest.find(interest), introduce);
    }

    private Group(String name, Interest interest, String introduce) {
        this.name = name;
        this.interest = interest;
        this.introduce = introduce;
    }

    public void putGroupMember(GroupMember groupMember) {
        this.groupMembers.add(groupMember);
    }

    public void modify(String name, String interest, String introduce) {
        this.name = name;
        this.interest = Interest.find(interest);
        this.introduce = introduce;
    }
}
