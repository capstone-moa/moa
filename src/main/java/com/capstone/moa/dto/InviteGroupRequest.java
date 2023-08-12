package com.capstone.moa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InviteGroupRequest {

    private Long groupId;
    private String inviteEmail;
}
