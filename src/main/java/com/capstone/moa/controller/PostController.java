package com.capstone.moa.controller;

import com.capstone.moa.dto.FindPostsByInterestResponse;
import com.capstone.moa.dto.FindPostsResponse;
import com.capstone.moa.dto.WritePostRequest;
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
    public ResponseEntity<Void> writePost(@RequestBody WritePostRequest postRequest) {
        postService.writePost(postRequest);
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
}
