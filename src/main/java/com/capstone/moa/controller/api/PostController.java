package com.capstone.moa.controller.api;

import com.capstone.moa.dto.*;
import com.capstone.moa.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 등록")
    @PostMapping
    public ResponseEntity<Void> writePost(@RequestBody WritePostRequest request, String email) {
        postService.writePost(request, email);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "모든 게시글 조회")
    @GetMapping
    public ResponseEntity<FindPostsResponse> findAllPosts() {
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @Operation(summary = "선택된 관심사에 해당하는 모든 게시물 조회")
    @GetMapping("/interest")
    public ResponseEntity<FindPostsByInterestResponse> findPostsByInterest(@RequestParam("interest") String interest) {
        return ResponseEntity.ok(postService.findPostsByInterest(interest));
    }

    @Operation(summary = "게시물 아이디로 게시물 상세 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<FindPostResponse> findPostById(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    @Operation(summary = "게시물 아이디로 게시물 상세 조회 댓글포함")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<FindPostWithCommentsResponse> findPostWithCommentsById(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.findPostWithCommentsByPostId(postId));
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{postId}")
    public ResponseEntity<Void> modifyPost(@PathVariable("postId") Long postId, @RequestBody ModifyPostRequest request) {
        postService.modifyPost(postId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId, @RequestBody String email) {
        postService.deletePost(postId, email);
        return ResponseEntity.noContent().build();
    }
}
