package com.capstone.moa.service;

import com.capstone.moa.dto.FindInvitationResponse;
import com.capstone.moa.dto.InviteGroupRequest;
import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.Invitation;
import com.capstone.moa.entity.enums.InviteStatus;
import com.capstone.moa.entity.Member;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.GroupRepository;
import com.capstone.moa.repository.InvitationRepository;
import com.capstone.moa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;
    private final InvitationRepository invitationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void inviteToGroup(InviteGroupRequest request) {
        GroupMember leader = findGroupLeader(request.getGroupId(), request.getLeaderEmail());
        Member member = memberRepository.findByEmail(request.getInviteEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (!leader.isGroupLeader()) {
            throw new IllegalArgumentException("You are not the leader of this group");
        }
        if (groupMemberRepository.existsGroupMemberByGroupAndMember(leader.getGroup(), member)) {
            throw new IllegalArgumentException(request.getInviteEmail() + " is already a GroupMember");
        }
        if (invitationRepository.existsByGroupAndMember(leader.getGroup(), member)) {
            throw new IllegalArgumentException("Invitation already sent");
        }

        invitationRepository.save(new Invitation(member, leader.getGroup()));
    }

    @Transactional
    public void acceptInvite(Long memberId, Long inviteId) {
        Invitation invitation = invitationRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("Invitation request not found"));
        if (!Objects.equals(invitation.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("It's not your invitation");
        }

        invitation.accept();
        groupMemberRepository.save(new GroupMember(invitation.getGroup(), invitation.getMember(), "Member"));
    }

    @Transactional
    public void rejectInvite(Long memberId, Long inviteId) {
        Invitation invitation = invitationRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("Invitation request not found"));
        if (!Objects.equals(invitation.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("It's not your invitation");
        }

        invitation.reject();
    }

    //leader의 memberId, 추방하려는 member의 groupMemberId 입력 받아야 함
    @Transactional
    public void removeGroupMember(Long memberId, Long groupMemberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        GroupMember groupMember = groupMemberRepository.findById(groupMemberId)
                .orElseThrow(() -> new IllegalArgumentException("GroupMember Not found"));

        GroupMember leader = groupMemberRepository.findGroupMemberByGroupAndMember(groupMember.getGroup(), member)
                .orElseThrow(() -> new IllegalArgumentException("GroupMember Not found"));
        if (!leader.isGroupLeader()) {
            throw new IllegalArgumentException("You are not the leader of this group");
        }

        Invitation invitation = invitationRepository.findByMemberAndGroup(groupMember.getMember(), groupMember.getGroup())
                .orElseThrow(() -> new IllegalArgumentException("Invitation Not found"));

        invitation.remove();
        groupMemberRepository.delete(groupMember);
    }

    @Transactional(readOnly = true)
    public List<FindInvitationResponse> findInvitationsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        return invitationRepository.findAllByMemberAndInviteStatus(member, InviteStatus.REQUEST)
                .stream()
                .map(FindInvitationResponse::from)
                .toList();
    }

    private GroupMember findGroupLeader(Long groupId, String email) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        GroupMember leader = groupMemberRepository.findGroupMemberByGroupAndMember(group, member)
                .orElseThrow(() -> new IllegalArgumentException("GroupMember not found"));
        if (!leader.isGroupLeader()) {
            throw new IllegalArgumentException("You are not the Leader of this group");
        }
        return leader;
    }
}
