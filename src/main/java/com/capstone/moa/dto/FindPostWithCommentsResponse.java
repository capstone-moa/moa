package com.capstone.moa.dto;

import com.capstone.moa.entity.Post;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public record FindPostWithCommentsResponse(
        Long postId, String title, String content, String interest, String memberEmail, Long memberId, String memberName, String memberJob,
        String createdDateTime, List<FindCommentResponse> comments
) {

    public static FindPostWithCommentsResponse from(Post post) {
        return new FindPostWithCommentsResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getInterest().name(),
                post.getMember().getEmail(),
                post.getMember().getId(),
                post.getMember().getName(),
                post.getMember().getJob().name(),
                post.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                post.getComments().stream()
                        .map(FindCommentResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
