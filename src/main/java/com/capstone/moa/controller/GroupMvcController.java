package com.capstone.moa.controller;

import com.capstone.moa.dto.GroupIntroResponse;
import com.capstone.moa.dto.ModifyGroupIntroRequest;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupMvcController {

    private final GroupService groupService;

    @GetMapping("/intro/{groupId}")
    public String findGroupIntroById(@PathVariable Long groupId, Model model) {
        GroupIntroResponse response = groupService.findGroupById(groupId);
        model.addAttribute("groupIntro", response);

        return "group/group_intro";
    }

    @GetMapping("/intro/modify/{groupId}")
    public String getModifyGroupIntro(@PathVariable Long groupId, Model model) {
        GroupIntroResponse response = groupService.findGroupById(groupId);
        model.addAttribute("groupIntro", response);
        model.addAttribute("modifyGroupIntroRequest", new ModifyGroupIntroRequest());

        return "group/group_intro_modify";
    }

    @PutMapping("/intro/modify/{groupId}")
    public String modifyGroupIntro(@PathVariable Long groupId, ModifyGroupIntroRequest request) {
        groupService.modifyGroupIntro(groupId, request);
        return "redirect:/group/intro/{groupId}";
    }

    @ModelAttribute("interests")
    private Interest[] putInterest() {
        return Interest.values();
    }
}
