package com.capstone.moa.entity;

public enum PostType {

    COMMUNITY,
    TEAM;

    public static PostType find(String postType) {
        return PostType.valueOf(postType.toUpperCase());
    }
}
