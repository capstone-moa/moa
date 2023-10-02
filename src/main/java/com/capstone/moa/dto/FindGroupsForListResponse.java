package com.capstone.moa.dto;

import com.capstone.moa.entity.Group;

public record FindGroupsForListResponse(
        Long groupId, String groupName, String groupIntro
) {
    public static FindGroupsForListResponse from(Group group) {
        return new FindGroupsForListResponse(
                group.getId(),
                group.getName(),
                group.getIntroduce()
        );
    }
}
