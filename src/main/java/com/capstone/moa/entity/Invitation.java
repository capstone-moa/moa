package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private InviteStatus inviteStatus;

    public Invitation(Member member, Group group) {
        this.member = member;
        this.group = group;
        this.inviteStatus = InviteStatus.REQUEST;
    }

    public void accept() {
        this.inviteStatus = InviteStatus.ACCEPT;
    }
}
