package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.Invite;
import com.capstone.moa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    boolean existsByGroupAndMember(Group group, Member member);
}
