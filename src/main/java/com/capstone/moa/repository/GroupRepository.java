package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByName(String name);

    @Query(value = "select g from Group g order by g.createdDateTime desc")
    List<Group> findAllOrderByCreatedDate();
}
