package com.capstone.moa.controller;

import com.capstone.moa.dto.CreateEventRequest;
import com.capstone.moa.dto.FindEventsByGroupResponse;
import com.capstone.moa.dto.GroupIntroResponse;
import com.capstone.moa.service.EventService;
import com.capstone.moa.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/group/calendar")
public class CalendarMvcController {

    private final EventService eventService;
    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public String calendar(@PathVariable("groupId") Long groupId, Model model) {

        GroupIntroResponse groupIntro = groupService.findGroupById(groupId);
        model.addAttribute("groupIntro", groupIntro);
        return "group/calendar";
    }

    //아직 인가 적용 안함
    @GetMapping("/{groupId}/list")
    public ResponseEntity<List<FindEventsByGroupResponse>> findEvents(@PathVariable("groupId") Long groupId) {
        List<FindEventsByGroupResponse> events = eventService.findAllEventsByGroup(groupId);
        return ResponseEntity.ok(events);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveEvent(@RequestBody CreateEventRequest request) {
        log.info("object : {}", request.getTitle());
        eventService.createEvent(request);
        return ResponseEntity.noContent().build();
    }
}
