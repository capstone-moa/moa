package com.capstone.moa.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindPostsByConditionRequest {

    private String postType;
    private String interest;
    private String region;

    @Builder
    public FindPostsByConditionRequest(String postType, String interest, String region) {
        this.postType = postType;
        this.interest = interest;
        this.region = region;
    }
}
