package com.capstone.moa.entity;

public enum InviteStatus {

    REQUEST,
    REJECT,
    ACCEPT,
    REMOVE
    ;

    public static InviteStatus find(String inviteStatus) {
        return InviteStatus.valueOf(inviteStatus.toUpperCase());
    }
}
