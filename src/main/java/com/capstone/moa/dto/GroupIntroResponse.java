package com.capstone.moa.dto;

import com.capstone.moa.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

public record GroupIntroResponse(
        Long id, String name, String interest, String introduce, String projectDescription,
        String skills, List<FindGroupMemberResponse> groupMembers
) {
    public static GroupIntroResponse from(Group group) {
        return new GroupIntroResponse(
                group.getId(),
                group.getName(),
                group.getInterest().name(),
                group.getIntroduce(),
                group.getProjectDescription(),
                group.getSkills(),
                group.getGroupMembers()
                        .stream()
                        .map(FindGroupMemberResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
