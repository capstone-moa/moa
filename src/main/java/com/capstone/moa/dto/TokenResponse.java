package com.capstone.moa.dto;

public record TokenResponse(String token) {
    public static TokenResponse of(String token) {
        return new TokenResponse(token);
    }
}
