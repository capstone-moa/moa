package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    Optional<GroupMember> findGroupMemberByGroupAndMember(Group group, Member member);

    boolean existsGroupMemberByGroupAndMember(Group group, Member member);
}
