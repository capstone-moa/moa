package com.capstone.moa.controller;

import com.capstone.moa.dto.FindGroupsForListResponse;
import com.capstone.moa.dto.FindMemberResponse;
import com.capstone.moa.dto.GroupIntroResponse;
import com.capstone.moa.dto.ModifyGroupIntroRequest;
import com.capstone.moa.dto.UserDetailsImpl;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.service.GroupProfileService;
import com.capstone.moa.service.GroupService;
import com.capstone.moa.service.LinkService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupMvcController {

    private final GroupService groupService;
    private final LinkService linkService;
    private final GroupProfileService groupProfileService;

    @GetMapping("/intro/{groupId}")
    public String findGroupIntroById(@PathVariable Long groupId, Model model) throws IOException {
        GroupIntroResponse response = groupService.findGroupById(groupId);
        FindMemberResponse leader = groupService.findGroupLeaderByGroupId(groupId);
        String groupProfile = groupProfileService.downloadImage(groupId);
        model.addAttribute("groupIntro", response);
        model.addAttribute("leader", leader);
        model.addAttribute("groupProfile", groupProfile);
        return "group/group_intro";
    }

    @GetMapping("/intro/modify/{groupId}")
    public String getModifyGroupIntro(@PathVariable Long groupId, Model model) throws IOException {
        GroupIntroResponse response = groupService.findGroupById(groupId);
        String groupProfile = groupProfileService.downloadImage(groupId);
        model.addAttribute("groupProfile", groupProfile);
        model.addAttribute("groupIntro", response);
        model.addAttribute("modifyGroupIntroRequest", new ModifyGroupIntroRequest());

        return "group/group_intro_modify";
    }

    @PutMapping("/intro/modify/{groupId}")
    public String modifyGroupIntro(@PathVariable Long groupId, ModifyGroupIntroRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        groupService.modifyGroupIntro(groupId, request, userDetails.getUsername());
        linkService.modifyGroupIntroLink(groupId, request.getGithub(), request.getProjectLink());
        return "redirect:/group/intro/{groupId}";
    }

    @GetMapping("/grouplist")
    public String findGroups(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) throws IOException {
        List<FindGroupsForListResponse> groups = null;
        if (userDetails != null) {
            groups = groupService.findGroupsByMemberId(userDetails.getMemberId());
        } else {
            groups = groupService.findAllGroups();
        }
        model.addAttribute("groups", groups);
        return "group/grouplist";
    }

    @GetMapping("/{groupId}/profile/write")
    public String uploadGroupProfileForm(@PathVariable Long groupId, Model model) {
        model.addAttribute("groupId", groupId);
        return "group/group_profile_upload";
    }

    @PostMapping("/{groupId}/profile/save")
    public String saveGroupProfile(@PathVariable Long groupId, @RequestBody MultipartFile file)
        throws Exception {
        groupProfileService.uploadGroupProfile(file, groupId);
        return "redirect:/";
    }

    @PostMapping("/{groupId}/profile/delete")
    public String deleteGroupProfile(@PathVariable Long groupId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        groupProfileService.deleteGroupProfile(groupId, userDetails.getMemberId());
        return "redirect:/";
    }

    @ModelAttribute("interests")
    private Interest[] putInterest() {
        return Interest.values();
    }
}
