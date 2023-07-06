package com.capstone.moa.controller;

import com.capstone.moa.dto.GroupIntroResponse;
import com.capstone.moa.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupMvcController {

    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public String findGroupIntroById(@PathVariable Long groupId, Model model) {
        GroupIntroResponse response = groupService.findGroupById(groupId);
        model.addAttribute("groupIntro", response);

        return "group/group_intro";
    }
}