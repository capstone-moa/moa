package com.capstone.moa.controller;

import com.capstone.moa.dto.FindNoticeByGroupIdResponse;
import com.capstone.moa.dto.GroupIntroResponse;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.service.GroupService;
import com.capstone.moa.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group/notice")
public class NoticeMvcController {

    private final GroupService groupService;
    private final NoticeService noticeService;

    @GetMapping("/{groupId}")
    public String findAllNotices(@PathVariable("groupId") Long groupId, Model model) {
        GroupIntroResponse response = groupService.findGroupById(groupId);
        List<FindNoticeByGroupIdResponse> notices = noticeService.findNoticesByGroupId(groupId);
        model.addAttribute("groupIntro", response);
        model.addAttribute("notices", notices);
        return "group/group_notice";
    }

    @GetMapping("/write")
    public String getWriteFrom(Model model) {
        return "group/group_notice_write";
    }

    @ModelAttribute("interests")
    private Interest[] putInterest() {
        return Interest.values();
    }
}
