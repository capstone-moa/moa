package com.capstone.moa.dto;

import com.capstone.moa.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

public record FindPostWithCommentsResponse(
        Long postId, String title, String content, String interest,
        List<FindCommentResponse> comments
) {

    public static FindPostWithCommentsResponse from(Post post) {
        return new FindPostWithCommentsResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getInterest().name(),
                post.getComments().stream()
                        .map(FindCommentResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
