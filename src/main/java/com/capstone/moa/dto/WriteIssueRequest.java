package com.capstone.moa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WriteIssueRequest {

    private Long groupId;
    private String title;
    private String content;
}
