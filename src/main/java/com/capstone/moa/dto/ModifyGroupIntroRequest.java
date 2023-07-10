package com.capstone.moa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyGroupIntroRequest {

    private String email;
    private String interest;
    private String projectDescription;
    private String skills;
}
