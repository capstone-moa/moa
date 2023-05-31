package com.capstone.moa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModifyPostRequest {

    private String email;
    private String title;
    private String content;
    private String interest;
}
