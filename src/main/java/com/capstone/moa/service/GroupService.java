package com.capstone.moa.service;

import com.capstone.moa.dto.CreateGroupRequest;
import com.capstone.moa.dto.InviteGroupRequest;
import com.capstone.moa.dto.ModifyGroupRequest;
import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.Invite;
import com.capstone.moa.entity.Member;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.GroupRepository;
import com.capstone.moa.repository.InviteRepository;
import com.capstone.moa.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final InviteRepository inviteRepository;

    @Transactional
    public void createGroup(CreateGroupRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        if (groupRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Duplicate group name");
        }

        Group group = new Group(request.getName(), request.getInterest(), request.getIntroduce());
        GroupMember groupMember = new GroupMember(group, member, "LEADER");
        group.putGroupMember(groupMember);

        groupRepository.save(group);
    }

    @Transactional
    public void modifyGroup(Long groupId, ModifyGroupRequest request) {
        GroupMember groupLeader = checkGroupMember(groupId, request.getEmail());
        isGroupLeader(groupLeader);

        groupLeader.getGroup().modify(request.getGroupName(), request.getInterest(), request.getIntroduce());
    }

    @Transactional
    public void deleteGroup(Long groupId, String email) {
        GroupMember groupLeader = checkGroupMember(groupId, email);
        isGroupLeader(groupLeader);

        groupRepository.delete(groupLeader.getGroup());
    }

    @Transactional
    public void inviteToGroup(Long groupId, InviteGroupRequest request) {
        GroupMember leader = checkGroupMember(groupId, request.getLeaderEmail());
        isGroupLeader(leader);
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (groupMemberRepository.existsGroupMemberByGroupAndMember(leader.getGroup(), member)) {
            throw new IllegalArgumentException(request.getEmail() + " is already a GroupMember");
        }

        inviteRepository.save(new Invite(member, leader.getGroup()));
    }

    private GroupMember checkGroupMember(Long groupId, String email) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return groupMemberRepository.findGroupMemberByGroupAndMember(group, member)
                .orElseThrow(() -> new IllegalArgumentException("GroupMember not found"));
    }

    private void isGroupLeader(GroupMember groupMember) {
        if (!groupMember.isGroupLeader()) {
            throw new IllegalArgumentException("You are not the leader of this group");
        }
    }
}
