package com.capstone.moa.dto;

import com.capstone.moa.entity.Comment;

import java.time.LocalDateTime;

public record FindCommentResponse(
        Long commentId, String content, String memberName,
        LocalDateTime createdAt

) {
    public static FindCommentResponse from(Comment comment) {
        return new FindCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getName(),
                comment.getCreatedDateTime()
        );

    }
}
