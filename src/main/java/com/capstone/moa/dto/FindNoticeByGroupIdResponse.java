package com.capstone.moa.dto;

import com.capstone.moa.entity.Notice;

public record FindNoticeByGroupIdResponse(
        Long noticeId, String title, String content
) {
    public static FindNoticeByGroupIdResponse from(Notice notice) {
        return new FindNoticeByGroupIdResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent()
        );
    }
}
