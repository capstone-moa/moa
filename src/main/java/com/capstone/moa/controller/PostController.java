package com.capstone.moa.controller;

import com.capstone.moa.dto.*;
import com.capstone.moa.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> writePost(@RequestBody WritePostRequest request) {
        postService.writePost(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<FindPostsResponse> findAllPosts() {
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @GetMapping
    public ResponseEntity<FindPostsByInterestResponse> findPostsByInterest(@RequestParam("interest") String interest) {
        return ResponseEntity.ok(postService.findPostsByInterest(interest));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<FindPostResponse> findPostById(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> modifyPost(@PathVariable("postId") Long postId, @RequestBody ModifyPostRequest request) {
        postService.modifyPost(postId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId, @RequestBody String memberId) {
        postService.deletePost(postId, memberId);
        return ResponseEntity.noContent().build();
    }
}
