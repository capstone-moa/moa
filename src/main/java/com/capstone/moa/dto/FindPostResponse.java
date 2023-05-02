package com.capstone.moa.dto;

import com.capstone.moa.entity.Post;

import java.time.LocalDateTime;

public record FindPostResponse(
    Long postId, String title, String content, String interest,
    String memberName, LocalDateTime createdAt
) {

    public static FindPostResponse from(Post post) {
        return new FindPostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getInterest().name(),
            post.getMember().getName(),
            post.getCreatedDateTime()
        );
    }
}
