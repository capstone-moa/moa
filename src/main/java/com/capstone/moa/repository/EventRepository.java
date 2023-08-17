package com.capstone.moa.repository;

import com.capstone.moa.entity.Event;
import com.capstone.moa.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByGroup(Group group);
}
