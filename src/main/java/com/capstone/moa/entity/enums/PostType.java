package com.capstone.moa.entity.enums;

public enum PostType {

    COMMUNITY,
    TEAM;

    public static PostType find(String postType) {
        return PostType.valueOf(postType.toUpperCase());
    }
}
