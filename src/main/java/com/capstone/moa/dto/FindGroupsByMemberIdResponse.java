package com.capstone.moa.dto;

import com.capstone.moa.entity.Group;

public record FindGroupsByMemberIdResponse(
        Long groupId, String groupName
) {
    public static FindGroupsByMemberIdResponse from(Group group) {
        return new FindGroupsByMemberIdResponse(
                group.getId(),
                group.getName()
        );
    }
}
