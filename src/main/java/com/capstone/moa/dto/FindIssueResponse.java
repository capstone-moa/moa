package com.capstone.moa.dto;

import com.capstone.moa.entity.Issue;

import java.time.format.DateTimeFormatter;

public record FindIssueResponse(
        Long id, String writer, String title, String content, String createdDateTime, String email,
        String profileImage
) {
    public static FindIssueResponse from(Issue issue) {
        return new FindIssueResponse(
                issue.getId(),
                issue.getGroupMember().getMember().getName(),
                issue.getTitle(),
                issue.getContent(),
                issue.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                issue.getGroupMember().getMember().getEmail(),
                issue.getGroupMember().getMember().getProfile().getProfileName()
        );
    }
}
