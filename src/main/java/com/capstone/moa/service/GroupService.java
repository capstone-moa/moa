package com.capstone.moa.service;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.enums.GroupRole;
import com.capstone.moa.entity.Member;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.GroupRepository;
import com.capstone.moa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

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
    public void modifyGroupInfo(Long groupId, ModifyGroupInfoRequest request) {
        GroupMember groupLeader = checkGroupMember(groupId, request.getEmail());
        isGroupLeader(groupLeader);

        groupLeader.getGroup().modifyGroupInfo(request.getIntroduce());
    }

    @Transactional
    public void modifyGroupIntro(Long groupId, ModifyGroupIntroRequest request) {
        GroupMember groupLeader = checkGroupMember(groupId, request.getEmail());
        isGroupLeader(groupLeader);

        groupLeader.getGroup().modifyGroupIntro(request.getInterest(), request.getProjectDescription(), request.getSkills());
    }

    @Transactional
    public void deleteGroup(Long groupId, String email) {
        GroupMember groupLeader = checkGroupMember(groupId, email);
        isGroupLeader(groupLeader);

        groupRepository.delete(groupLeader.getGroup());
    }

    @Transactional(readOnly = true)
    public GroupIntroResponse findGroupById(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        return GroupIntroResponse.from(group);
    }

    @Transactional(readOnly = true)
    public List<FindGroupByLeaderMemberIdResponse> findGroupsByLeaderMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        List<GroupMember> groupMembers = groupMemberRepository.findAllByMemberAndGroupRole(member, GroupRole.LEADER);
        List<Group> groups = groupMembers.stream().map(GroupMember::getGroup).toList();
        return groups
                .stream()
                .map(FindGroupByLeaderMemberIdResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FindGroupsByMemberIdResponse> findGroupsByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List<GroupMember> groupMembers = groupMemberRepository.findAllByMember(member);
        List<Group> groups = groupMembers.stream().map(GroupMember::getGroup).toList();
        return groups
                .stream()
                .map(FindGroupsByMemberIdResponse::from)
                .collect(Collectors.toList());
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
