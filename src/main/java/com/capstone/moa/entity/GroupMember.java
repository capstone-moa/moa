package com.capstone.moa.entity;

import com.capstone.moa.entity.enums.GroupRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private GroupRole groupRole;

    public GroupMember(Group group, Member member, String groupRole) {
        this(group, member, GroupRole.find(groupRole));
    }

    private GroupMember(Group group, Member member, GroupRole groupRole) {
        this.group = group;
        this.member = member;
        this.groupRole = groupRole;
    }

    public boolean isGroupLeader() {
        return this.groupRole.equals(GroupRole.LEADER);
    }
}
