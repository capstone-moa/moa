package com.capstone.moa.controller;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.service.CommentService;
import com.capstone.moa.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
public class PostMvcController {

    private final PostService postService;
    private final CommentService commentService;
    private final String COMMUNITY = "COMMUNITY";
    private final String TEAM = "TEAM";

    @GetMapping
    public String findAllPosts(Model model) {
        FindPostsResponse communityList = postService.findPostsByPostType(COMMUNITY);
        FindPostsResponse teamList = postService.findPostsByPostType(TEAM);
        model.addAttribute("communityPosts", communityList.posts());
        model.addAttribute("teamPosts", teamList.posts());

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
    public String save(@AuthenticationPrincipal UserDetailsImpl userDetails, WritePostRequest request) {
        postService.writePost(request, userDetails.getUsername());
        return "redirect:/posts";
    }

    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUsername());
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

    @PostMapping("/comment")
    public ResponseEntity<?> writeComment(@RequestBody WriteCommentRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.writeComment(request, userDetails.getUsername());
        return ResponseEntity.ok("댓글이 등록되었습니다.");
    }

    @GetMapping("/search")
    public String searchPosts(
            @RequestParam(value = "title", defaultValue = "", required = false) String title,
            @RequestParam(value = "postType", defaultValue = "", required = false) String postType,
            Model model
    ) {
        SearchPostRequest request = SearchPostRequest.builder()
                .title(title)
                .postType(postType)
                .build();
        Pageable pageable = PageRequest.of(0, 6);
        System.out.println("title : " + title);
        List<FindPostResponse> posts = postService.searchPostsByTitleAndType(request, pageable);

        model.addAttribute("posts", posts);
        return "post/search";
    }
}
