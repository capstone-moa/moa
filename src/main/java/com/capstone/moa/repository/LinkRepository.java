package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

    boolean existsLinkByGroup(Group group);

}
