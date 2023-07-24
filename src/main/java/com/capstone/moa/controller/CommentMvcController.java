package com.capstone.moa.controller;

import com.capstone.moa.dto.WriteCommentRequest;
import com.capstone.moa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentMvcController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}")
    public String writeComment(@PathVariable("postId") Long postId, WriteCommentRequest request) {
        commentService.writeComment(postId, request);
        return "redirect:/posts/{postId}";
    }
}
