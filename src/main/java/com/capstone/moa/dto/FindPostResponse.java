package com.capstone.moa.dto;

import com.capstone.moa.entity.Post;

import java.time.format.DateTimeFormatter;

public record FindPostResponse(
        Long postId, String title, String content, String interest,
        String memberName, String memberJob, String createdAt, int commentCnt
) {

    public static FindPostResponse from(Post post) {
        return new FindPostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getInterest().name(),
                post.getMember().getName(),
                post.getMember().getJob().name(),
                post.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                post.getComments().size()
        );
    }
}
