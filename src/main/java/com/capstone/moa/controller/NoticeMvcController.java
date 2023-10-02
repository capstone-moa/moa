package com.capstone.moa.controller;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.service.GroupProfileService;
import com.capstone.moa.service.GroupService;
import com.capstone.moa.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group/notice")
public class NoticeMvcController {

    private final GroupService groupService;
    private final NoticeService noticeService;
    private final GroupProfileService groupProfileService;

    @GetMapping("/{groupId}")
    public String findAllNotices(@PathVariable("groupId") Long groupId, Model model) throws IOException {
        GroupIntroResponse response = groupService.findGroupById(groupId);
        List<FindNoticeByGroupIdResponse> notices = noticeService.findNoticesByGroupId(groupId);
        FindMemberResponse leader = groupService.findGroupLeaderByGroupId(groupId);
        String groupProfile = groupProfileService.downloadImage(groupId);
        model.addAttribute("groupProfile", groupProfile);
        model.addAttribute("groupIntro", response);
        model.addAttribute("notices", notices);
        model.addAttribute("leader", leader);
        return "group/group_notice";
    }

    @GetMapping("/{groupId}/write")
    public String getWriteFrom(@PathVariable("groupId") Long groupId, Model model) {
        model.addAttribute("WriteNoticeRequest", new WriteNoticeRequest());
        model.addAttribute("groupId", groupId);

        return "group/group_notice_write";
    }

    @PostMapping("/save")
    public String saveNotice(WriteNoticeRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeService.createNotice(request, userDetails.getUsername());
        return "redirect:/";
    }

    @PostMapping("/{noticeId}/delete")
    public String deleteNotice(@PathVariable("noticeId") Long noticeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long groupId = noticeService.deleteNotice(noticeId, userDetails.getUsername());
        return "redirect:/group/notice/" + groupId;
    }

    @ModelAttribute("interests")
    private Interest[] putInterest() {
        return Interest.values();
    }
}
