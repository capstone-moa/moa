package com.capstone.moa.dto;

import com.capstone.moa.entity.GroupMember;

public record FindGroupMemberResponse(
        Long id, String email, String job, String role, String name, Long memberId, String memberProfile
) {
    public static FindGroupMemberResponse from(GroupMember groupMember) {
        return new FindGroupMemberResponse(
                groupMember.getId(),
                groupMember.getMember().getEmail(),
                groupMember.getMember().getJob().name(),
                groupMember.getGroupRole().name(),
                groupMember.getMember().getName(),
                groupMember.getMember().getId(),
                groupMember.getMember().getProfile().getProfileName()
        );
    }
}
