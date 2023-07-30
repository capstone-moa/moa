package com.capstone.moa.controller;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.service.GroupService;
import com.capstone.moa.service.InvitationService;
import com.capstone.moa.service.MemberService;
import com.capstone.moa.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MypageMvcController {

    private final PostService postService;
    private final GroupService groupService;
    private final InvitationService invitationService;
    private final MemberService memberService;

    @GetMapping("/{memberId}/activity")
    public String findMemberActivities(@PathVariable("memberId") Long memberId, Model model) {
        FindPostsResponse postList = postService.findPostsByMember(memberId);
        FindMemberByIdResponse member = memberService.findMemberById(memberId);

        model.addAttribute("member", member);
        model.addAttribute("posts", postList.posts());
        return "mypage/mypage_activity";
    }

    @GetMapping("/{memberId}/group")
    public String findMemberGroups(@PathVariable("memberId") Long memberId, Model model) {
        List<FindGroupByLeaderMemberIdResponse> groups = groupService.findGroupsByLeaderMemberId(memberId);
        List<FindGroupsByMemberIdResponse> groupsForCategory = groupService.findGroupsByMemberId(memberId);
        List<FindInvitationResponse> invitations = invitationService.findInvitationsByMember(memberId);
        FindMemberByIdResponse member = memberService.findMemberById(memberId);

        model.addAttribute("member", member);
        model.addAttribute("groups", groups);
        model.addAttribute("groupCategories", groupsForCategory);
        model.addAttribute("invitations", invitations);

        model.addAttribute("createGroupRequest", new CreateGroupRequest());
        return "mypage/mypage_group";
    }

    @GetMapping("/group/create")
    public String getCreateGroupForm(Model model) {
        model.addAttribute("createGroupRequest", new CreateGroupRequest());
        return "mypage/mypage_group_write";
    }

    @PostMapping("/group/save")
    public String saveGroup(CreateGroupRequest request) {
        groupService.createGroup(request);
        return "redirect:/";
    }

    @GetMapping("/{memberId}/group/{inviteId}/accept")
    public String pushAcceptBtn(@PathVariable("memberId") Long memberId, @PathVariable("inviteId") Long inviteId) {
        invitationService.acceptInvite(memberId, inviteId);
        return "redirect:/mypage/{memberId}/group";
    }

    @GetMapping("/{memberId}/group/{inviteId}/reject")
    public String pushRejectBtn(@PathVariable("memberId") Long memberId, @PathVariable("inviteId") Long inviteId) {
        invitationService.rejectInvite(memberId, inviteId);
        return "redirect:/mypage/{memberId}/group";
    }

    @GetMapping("/{leaderMemberId}/group/{groupMemberId}/remove")
    public String pushRemoveBtn(@PathVariable("leaderMemberId") Long leaderMemberId, @PathVariable("groupMemberId") Long groupMemberId) {
        invitationService.removeGroupMember(leaderMemberId, groupMemberId);
        return "redirect:/mypage/{leaderMemberId}/group";
    }

    @ModelAttribute("interests")
    private Interest[] putInterest() {
        return Interest.values();
    }
}