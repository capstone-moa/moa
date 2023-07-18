package com.capstone.moa.dto;

import com.capstone.moa.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

public record FindGroupByLeaderMemberIdResponse(
        Long groupId, String groupName, List<FindGroupMemberResponse> groupMembers
) {
    public static FindGroupByLeaderMemberIdResponse from(Group group) {
        return new FindGroupByLeaderMemberIdResponse(
                group.getId(),
                group.getName(),
                group.getGroupMembers()
                        .stream()
                        .map(FindGroupMemberResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
