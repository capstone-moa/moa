package com.capstone.moa.controller.api;

import com.capstone.moa.dto.ModifyCommentRequest;
import com.capstone.moa.dto.WriteCommentRequest;
import com.capstone.moa.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class CommentController {

    private final CommentService commentService;


    @Operation(summary = "댓글 작성")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> writeComment(@PathVariable("postId") Long postId, @RequestBody WriteCommentRequest request) {
        commentService.writeComment(request, "test@email.com");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{postId}/comments")
    public ResponseEntity<Void> updateComment(@PathVariable("postId") Long postId, @RequestBody ModifyCommentRequest request) {
        commentService.modifyComment(postId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("//comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody String memberId
    ) {
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.noContent().build();
    }
}
