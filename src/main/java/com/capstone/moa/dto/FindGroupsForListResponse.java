package com.capstone.moa.dto;

import com.capstone.moa.entity.Group;

public record FindGroupsForListResponse(
    Long groupId, String groupName, String groupIntro, String image
) {

    public static FindGroupsForListResponse from(Group group, String image) {
        return new FindGroupsForListResponse(
            group.getId(),
            group.getName(),
            group.getIntroduce(),
            image
        );
    }
}
