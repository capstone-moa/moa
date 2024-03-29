package com.capstone.moa.dto;

import com.capstone.moa.entity.Post;

import java.time.format.DateTimeFormatter;

public record FindPostResponse(
        Long postId, String title, String interest, String postType,
        String memberName, String memberJob, String createdDateTime, int commentCnt, Long memberId, String memberProfileImage
) {

    public static FindPostResponse from(Post post) {
        return new FindPostResponse(
                post.getId(),
                post.getTitle(),
                post.getInterest().name(),
                post.getPostType().name(),
                post.getMember().getName(),
                post.getMember().getJob().name(),
                post.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                post.getComments().size(),
                post.getMember().getId(),
                post.getMember().getProfile().getProfileName()
        );
    }
}
