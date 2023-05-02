package com.capstone.moa.dto;

import com.capstone.moa.entity.Interest;

import java.util.List;

public record FindPostsByInterestResponse(
    Interest interest,
    List<FindPostByInterestResponse> posts) {
}
