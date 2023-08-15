package com.capstone.moa.controller;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.entity.enums.Job;
import com.capstone.moa.service.GroupService;
import com.capstone.moa.service.InvitationService;
import com.capstone.moa.service.MemberService;
import com.capstone.moa.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        FindMemberInfoResponse member = memberService.findMemberInfoById(memberId);

        model.addAttribute("member", member);
        model.addAttribute("posts", postList.posts());
        return "mypage/mypage_activity";
    }

    @GetMapping("/{memberId}/group")
    public String findMemberGroups(@PathVariable("memberId") Long memberId, Model model) {
        List<FindGroupByLeaderMemberIdResponse> groups = groupService.findGroupsByLeaderMemberId(memberId);
        List<FindGroupsByMemberIdResponse> groupsForCategory = groupService.findGroupsByMemberId(memberId);
        List<FindInvitationResponse> invitations = invitationService.findInvitationsByMember(memberId);
        FindMemberInfoResponse member = memberService.findMemberInfoById(memberId);

        model.addAttribute("member", member);
        model.addAttribute("groups", groups);
        model.addAttribute("groupCategories", groupsForCategory);
        model.addAttribute("invitations", invitations);

        model.addAttribute("createGroupRequest", new CreateGroupRequest());
        return "mypage/mypage_group";
    }

    @GetMapping("/{memberId}")
    public String modifyForm(@PathVariable Long memberId, Model model) {
        FindMemberInfoResponse memberInfo = memberService.findMemberInfoById(memberId);

        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("ModifyMemberRequest", new ModifyMemberRequest());
        return "/mypage/mypage_modify";
    }

    @PutMapping("/{memberId}")
    public String modifyMember(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails, ModifyMemberRequest request) {
        memberService.modifyMemberDetails(memberId, userDetails.getUsername(), request);
        return "redirect:/mypage/{memberId}/activity";
    }

    @GetMapping("/group/create")
    public String getCreateGroupForm(Model model) {
        model.addAttribute("createGroupRequest", new CreateGroupRequest());
        return "mypage/mypage_group_write";
    }

    @PostMapping("/group/save")
    public String saveGroup(CreateGroupRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        groupService.createGroup(request, userDetails.getUsername());
        return "redirect:/";
    }

    @PostMapping("/group/invite")
    public String inviteMember(@RequestBody InviteGroupRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        invitationService.inviteToGroup(request, userDetails.getUsername());
        return "redirect:/group/intro/" + request.getGroupId();
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

    @ModelAttribute("jobs")
    private Job[] putJob() {
        return Job.values();
    }
}
