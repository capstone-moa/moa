package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.GroupRole;
import com.capstone.moa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    Optional<GroupMember> findGroupMemberByGroupAndMember(Group group, Member member);

    boolean existsGroupMemberByGroupAndMember(Group group, Member member);

    List<GroupMember> findAllByMemberAndGroupRole(Member member, GroupRole groupRole);
}
