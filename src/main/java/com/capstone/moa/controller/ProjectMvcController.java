package com.capstone.moa.controller;

import com.capstone.moa.dto.FindIssueResponse;
import com.capstone.moa.dto.GroupInfoResponse;
import com.capstone.moa.dto.UserDetailsImpl;
import com.capstone.moa.dto.WriteIssueRequest;
import com.capstone.moa.service.GroupService;
import com.capstone.moa.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group/project")
public class ProjectMvcController {

    private final GroupService groupService;
    private final IssueService issueService;

    @GetMapping("/{groupId}/issue")
    public String findProject(@PathVariable("groupId") Long groupId, Model model) {
        GroupInfoResponse groupInfo = groupService.findGroupInfoById(groupId);
        List<FindIssueResponse> issueList = issueService.findIssueList(groupId);
        model.addAttribute("group", groupInfo);
        model.addAttribute("issueList", issueList);
        return "group/group_management_issue";
    }

    @GetMapping("/{groupId}/issue/write")
    public String issueForm(@PathVariable("groupId") Long groupId, Model model) {
        model.addAttribute("WriteIssueRequest", new WriteIssueRequest());
        model.addAttribute("groupId", groupId);
        return "group/group_issue_write";
    }

    @PostMapping("/issue/save")
    public String saveIssue(WriteIssueRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        issueService.writeIssue(request, userDetails.getMemberId());
        return "redirect:/";
    }


    @GetMapping("/{groupId}/files")
    public String findFiles(@PathVariable("groupId") Long groupId, Model model) {
        GroupInfoResponse groupInfo = groupService.findGroupInfoById(groupId);
        model.addAttribute("group", groupInfo);
        return "group/group_management_file";
    }
}
