package com.capstone.moa.dto;

import com.capstone.moa.entity.Issue;

import java.time.format.DateTimeFormatter;

public record FindIssueResponse(
        Long id, String writer, String title, String content, String createdDateTime
) {
    public static FindIssueResponse from(Issue issue) {
        return new FindIssueResponse(
                issue.getId(),
                issue.getGroupMember().getMember().getEmail(),
                issue.getTitle(),
                issue.getContent(),
                issue.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }
}
