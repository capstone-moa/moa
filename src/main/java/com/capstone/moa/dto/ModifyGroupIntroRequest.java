package com.capstone.moa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModifyGroupIntroRequest {

    private String interest;
    private String introduce;
    private String projectDescription;
    private String skills;
    private String github;
    private String projectLink;
}
