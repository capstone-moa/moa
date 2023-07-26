package com.capstone.moa.entity.enums;

public enum GroupRole {

    LEADER,
    MEMBER,
    ;

    public static GroupRole find(String groupRole) {
        return GroupRole.valueOf(groupRole.toUpperCase());
    }
}
