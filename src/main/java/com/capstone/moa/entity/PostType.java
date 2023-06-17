package com.capstone.moa.entity;

public enum PostType {

    COMMUNITY,
    RECRUIT;

    public static PostType find(String postType) {
        return PostType.valueOf(postType.toUpperCase());
    }
}
