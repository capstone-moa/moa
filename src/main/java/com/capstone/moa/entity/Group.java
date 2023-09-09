package com.capstone.moa.entity;

import com.capstone.moa.entity.enums.Interest;
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

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String projectDescription;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String skills;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupMember> groupMembers = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Invitation> invitations = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    @OneToOne(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Link link;

    @OneToOne(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> notices = new ArrayList<>();

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

    public void setLink(Link link) {
        this.link = link;
    }

    public void addAddress(Address address) {
        this.address = address;
    }

    public void modifyGroupIntro(String introduce, String interest, String projectDescription, String skills) {
        this.introduce = introduce;
        this.interest = Interest.find(interest);
        this.projectDescription = projectDescription;
        this.skills = skills;
    }
}
