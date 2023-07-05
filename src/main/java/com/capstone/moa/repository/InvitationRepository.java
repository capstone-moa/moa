package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.Invitation;
import com.capstone.moa.entity.InviteStatus;
import com.capstone.moa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    boolean existsByGroupAndMember(Group group, Member member);

    List<Invitation> findAllByMemberAndInviteStatus(Member member, InviteStatus inviteStatus);
}
