package com.capstone.moa.controller;

import com.capstone.moa.dto.FindPostsResponse;
import com.capstone.moa.entity.Interest;
import com.capstone.moa.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/member")
public class MemberMvcController {

    private final PostService postService;

    @GetMapping("/mypage/{memberId}")
    private String findPostsByMember(@PathVariable("memberId") Long memberId, Model model) {
        FindPostsResponse postList = postService.findPostsByMember(memberId);
        model.addAttribute("posts", postList.posts());
        return "member/mypage_activity";
    }

    @ModelAttribute("interests")
    private Interest[] putInterest() {
        return Interest.values();
    }
}
