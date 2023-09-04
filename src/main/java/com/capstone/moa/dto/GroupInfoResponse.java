package com.capstone.moa.dto;

import com.capstone.moa.entity.Group;

public record GroupInfoResponse(
        Long id, String name, String interest, String introduce,
        String github
) {
    public static GroupInfoResponse from(Group group) {
        return new GroupInfoResponse(
                group.getId(),
                group.getName(),
                group.getInterest().name(),
                group.getIntroduce(),
                group.getLink().getGithub()
        );
    }
}
