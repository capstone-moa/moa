package com.capstone.moa.dto;

import com.capstone.moa.entity.Post;

import java.time.LocalDateTime;

public record FindPostByInterestResponse(
    Long postId, String title, String content,
    String memberName, LocalDateTime createdAt
) {

    public static FindPostByInterestResponse from(Post post) {
        return new FindPostByInterestResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getMember().getName(),
            post.getCreatedDateTime()
        );
    }
}
