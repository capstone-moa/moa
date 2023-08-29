package com.capstone.moa.dto;

import com.capstone.moa.entity.Post;

public record PostDetailResponse(
        Long postId, String title, String interest, String postType, String content
) {
    public static PostDetailResponse from(Post post) {
        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getInterest().name(),
                post.getPostType().name(),
                post.getContent()
        );
    }
}
