package com.capstone.moa.service;

import com.capstone.moa.dto.InviteGroupRequest;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.Invite;
import com.capstone.moa.entity.Member;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.InviteRepository;
import com.capstone.moa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final GroupMemberRepository groupMemberRepository;
    private final InviteRepository inviteRepository;
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
        if (inviteRepository.existsByGroupAndMember(leader.getGroup(), member)) {
            throw new IllegalArgumentException("Invitation already sent");
        }

        inviteRepository.save(new Invite(member, leader.getGroup()));
    }

    @Transactional
    public void acceptInvite(Long memberId, Long inviteId) {
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("Invite request not found"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (!Objects.equals(invite.getMember(), member)) {
            throw new IllegalArgumentException("It's not your invitation");
        }
        invite.accept();

        groupMemberRepository.save(new GroupMember(invite.getGroup(), invite.getMember(), "Member"));
    }
}
