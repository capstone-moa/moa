package com.capstone.moa.repository;

import com.capstone.moa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    boolean existsByGroupAndMember(Group group, Member member);

    List<Invitation> findAllByMemberAndInviteStatus(Member member, InviteStatus inviteStatus);

    Optional<Invitation> findByMemberAndGroup(Member member, Group group);
}
