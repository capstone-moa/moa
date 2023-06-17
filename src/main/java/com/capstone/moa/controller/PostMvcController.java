package com.capstone.moa.controller;

import com.capstone.moa.dto.FindPostsResponse;
import com.capstone.moa.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
@Slf4j
public class PostMvcController {

    private final PostService postService;
    private final String COMMUNITY = "COMMUNITY";
    private final String RECRUIT = "RECRUIT";

    @GetMapping
    public String findAllPosts(Model model) {
        FindPostsResponse communityList = postService.findPostsByPostType(COMMUNITY);
        FindPostsResponse recruitList = postService.findPostsByPostType(RECRUIT);
        model.addAttribute("communityPosts", communityList.posts());
        model.addAttribute("recruitPosts", recruitList.posts());

        return "post/main";
    }

    @GetMapping("/community")
    public String communityList(Model model) {
        FindPostsResponse communityList = postService.findPostsByPostType(COMMUNITY);
        model.addAttribute("communityPosts", communityList.posts());
        return "post/community";
    }

    @GetMapping("/team")
    public String teamRecruitList(Model model) {
        FindPostsResponse recruitPosts = postService.findPostsByPostType(RECRUIT);
        model.addAttribute("recruitPosts", recruitPosts.posts());
        return "post/team";
    }

    /*-----------기능 구현 필요 ---------*/
    @GetMapping("/community/write")
    public String community() {
        return "post/community_write";
    }

    @GetMapping("/login")
    public String login() {
        return "post/login";
    }

    @GetMapping("/join")
    public String join() {
        return "post/join";
    }
}
