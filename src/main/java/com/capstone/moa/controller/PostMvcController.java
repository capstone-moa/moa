package com.capstone.moa.controller;

import com.capstone.moa.dto.FindPostWithCommentsResponse;
import com.capstone.moa.dto.FindPostsResponse;
import com.capstone.moa.dto.WritePostRequest;
import com.capstone.moa.entity.Interest;
import com.capstone.moa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
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
        return "post/view";
    }
}
