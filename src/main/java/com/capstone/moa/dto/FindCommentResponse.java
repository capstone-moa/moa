package com.capstone.moa.dto;

import com.capstone.moa.entity.Comment;

import java.time.format.DateTimeFormatter;

public record FindCommentResponse(
        Long commentId, String content, String writerEmail,
        String createdDateTime

) {
    public static FindCommentResponse from(Comment comment) {
        return new FindCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getEmail(),
                comment.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

    }
}
