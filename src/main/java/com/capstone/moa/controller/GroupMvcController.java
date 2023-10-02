package com.capstone.moa.controller;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.service.GroupService;
import com.capstone.moa.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupMvcController {

    private final GroupService groupService;
    private final LinkService linkService;

    @GetMapping("/intro/{groupId}")
    public String findGroupIntroById(@PathVariable Long groupId, Model model) {
        GroupIntroResponse response = groupService.findGroupById(groupId);
        FindMemberResponse leader = groupService.findGroupLeaderByGroupId(groupId);
        model.addAttribute("groupIntro", response);
        model.addAttribute("leader", leader);
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
    public String modifyGroupIntro(@PathVariable Long groupId, ModifyGroupIntroRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        groupService.modifyGroupIntro(groupId, request, userDetails.getUsername());
        linkService.modifyGroupIntroLink(groupId, request.getGithub(), request.getProjectLink());
        return "redirect:/group/intro/{groupId}";
    }

    @GetMapping("/grouplist")
    public String findGroups(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        List<FindGroupsForListResponse> groups = null;
        if (userDetails != null) {
            groups = groupService.findGroupsByMemberId(userDetails.getMemberId());
        }else {
            groups = groupService.findAllGroups();
        }
        model.addAttribute("groups", groups);
        return "group/grouplist";
    }

    @GetMapping("/{groupId}/upload-profile")
    public String uploadGroupProfile(@PathVariable Long groupId) {
        // "" 안에 프로필 업로드 폼 경로 넣어주세용
        return "group/업로드폼";
    }

    @ModelAttribute("interests")
    private Interest[] putInterest() {
        return Interest.values();
    }
}
