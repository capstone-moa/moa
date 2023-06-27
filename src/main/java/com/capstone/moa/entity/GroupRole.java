package com.capstone.moa.entity;

public enum GroupRole {

    LEADER,
    MEMBER,
    ;

    public static GroupRole find(String groupRole) {
        return GroupRole.valueOf(groupRole.toUpperCase());
    }
}
