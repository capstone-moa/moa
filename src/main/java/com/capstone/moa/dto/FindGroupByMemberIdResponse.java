package com.capstone.moa.dto;

import com.capstone.moa.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

public record FindGroupByMemberIdResponse(
        Long groupId, String groupName, List<FindGroupMemberResponse> groupMembers
) {
    public static FindGroupByMemberIdResponse from(Group group) {
        return new FindGroupByMemberIdResponse(
                group.getId(),
                group.getName(),
                group.getGroupMembers()
                        .stream()
                        .map(FindGroupMemberResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
