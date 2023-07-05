package com.capstone.moa.service;

import com.capstone.moa.dto.FindInvitationResponse;
import com.capstone.moa.dto.InviteGroupRequest;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.Invitation;
import com.capstone.moa.entity.InviteStatus;
import com.capstone.moa.entity.Member;
import com.capstone.moa.repository.GroupMemberRepository;
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
    private final InvitationRepository invitationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void inviteToGroup(InviteGroupRequest request) {
        GroupMember leader = groupMemberRepository.findById(request.getGroupMemberId())
                .orElseThrow(() -> new IllegalArgumentException("GroupMember Not found"));
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (!leader.isGroupLeader()) {
            throw new IllegalArgumentException("You are not the leader of this group");
        }
        if (groupMemberRepository.existsGroupMemberByGroupAndMember(leader.getGroup(), member)) {
            throw new IllegalArgumentException(request.getEmail() + " is already a GroupMember");
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

    @Transactional(readOnly = true)
    public List<FindInvitationResponse> findInvitationsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        return invitationRepository.findAllByMemberAndInviteStatus(member, InviteStatus.REQUEST)
                .stream()
                .map(FindInvitationResponse::from)
                .toList();
    }

}
