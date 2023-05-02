package com.capstone.moa.controller;

import com.capstone.moa.dto.WritePostRequest;
import com.capstone.moa.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> writePost(@RequestBody WritePostRequest postRequest) {
        postService.writePost(postRequest);
        return ResponseEntity.noContent().build();
    }
}
