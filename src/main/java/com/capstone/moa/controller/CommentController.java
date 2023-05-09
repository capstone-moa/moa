package com.capstone.moa.controller;

import com.capstone.moa.dto.WriteCommentRequest;
import com.capstone.moa.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> writeComment(@PathVariable("postId") Long postId, @RequestBody WriteCommentRequest request) {
        commentService.writeComment(postId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody String memberId
    ) {
        commentService.deleteComment(postId, commentId, memberId);
        return ResponseEntity.noContent().build();
    }
}
