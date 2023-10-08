package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupProfileRepository extends JpaRepository<GroupProfile, Long> {

    GroupProfile findGroupProfileByGroup(Group group);
}
