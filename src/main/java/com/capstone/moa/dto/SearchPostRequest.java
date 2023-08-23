package com.capstone.moa.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchPostRequest {

    private String title;
    private String postType;

    @Builder
    public SearchPostRequest(String title, String postType) {
        this.title = title;
        this.postType = postType;
    }
}
