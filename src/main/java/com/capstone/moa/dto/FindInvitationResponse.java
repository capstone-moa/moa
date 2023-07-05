package com.capstone.moa.dto;

import com.capstone.moa.entity.Invitation;

public record FindInvitationResponse(
        Long id, String groupName, String groupIntroduce
) {
    public static FindInvitationResponse from(Invitation invitation) {
        return new FindInvitationResponse(
                invitation.getId(),
                invitation.getGroup().getName(),
                invitation.getGroup().getIntroduce()
        );
    }
}
