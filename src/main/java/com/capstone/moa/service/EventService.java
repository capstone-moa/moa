package com.capstone.moa.service;

import com.capstone.moa.dto.CreateEventRequest;
import com.capstone.moa.dto.FindEventsByGroupResponse;
import com.capstone.moa.entity.Event;
import com.capstone.moa.entity.Group;
import com.capstone.moa.repository.EventRepository;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final GroupRepository groupRepository;
    private final EventRepository eventRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void createEvent(CreateEventRequest request) {
//        List<Long> groupMemberIds = groupMemberRepository.findGroupMembers(request.getGroupId());
//        if (!groupMemberIds.contains(memberId)) {
//            throw new IllegalArgumentException("You are not member of this group");
//        }
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        eventRepository.save(request.toEntity(group));
    }

    @Transactional(readOnly = true)
    public List<FindEventsByGroupResponse> findAllEventsByGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        List<Event> events = eventRepository.findAllByGroup(group);
        return events.stream()
                .map(FindEventsByGroupResponse::from)
                .collect(Collectors.toList());
    }
}
