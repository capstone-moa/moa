package com.capstone.moa.controller;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.service.MemberService;
import com.capstone.moa.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
public class PostMvcController {

    private final PostService postService;
    private final MemberService memberService;
    private final String COMMUNITY = "COMMUNITY";
    private final String TEAM = "TEAM";

    @GetMapping
    public String  findAllPosts(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        FindPostsResponse communityList = postService.findPostsByPostType(COMMUNITY);
        FindPostsResponse teamList = postService.findPostsByPostType(TEAM);
        String email = "";
        model.addAttribute("communityPosts", communityList.posts());
        model.addAttribute("teamPosts", teamList.posts());

        if (userDetails != null) {
            FindMemberResponse memberResponse = memberService.findMemberByEmail(userDetails.getUsername());
            email = memberResponse.email();
        }else {
            email = "아무도 로그인하지 않음";
        }
        log.info("login user : {}", email);

        return "post/main";
    }

    @GetMapping("/community")
    public String communityList(@RequestParam(value = "interest", required = false) String interest, Model model) {
        FindPostsResponse communityList = postService.findPostsByPostType(COMMUNITY);
        if (interest != null) {
            communityList = postService.findPostsByPostTypeAndInterest(COMMUNITY, interest);
        }
        model.addAttribute("communityPosts", communityList.posts());
        return "post/community";
    }

    @GetMapping("/team")
    public String teamRecruitList(@RequestParam(value = "interest", required = false) String interest, Model model) {
        FindPostsResponse teamPosts = postService.findPostsByPostType(TEAM);
        if (interest != null) {
            teamPosts = postService.findPostsByPostTypeAndInterest(TEAM, interest);
        }
        model.addAttribute("teamPosts", teamPosts.posts());
        return "post/team";
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("writePostRequest", new WritePostRequest());
        return "post/write";
    }

    @PostMapping("/write")
    public String save(WritePostRequest request) {
        postService.writePost(request);
        return "redirect:/posts";
    }

    @ModelAttribute("interests")
    private Interest[] putInterest() {
        return Interest.values();
    }

    @GetMapping("/{postId}")
    public String findPostDetailById(@PathVariable("postId") Long postId, Model model) {
        FindPostWithCommentsResponse postDetail = postService.findPostWithCommentsByPostId(postId);
        model.addAttribute("postDetail", postDetail);
        model.addAttribute("writeCommentRequest", new WriteCommentRequest());
        return "post/view";
    }
}
