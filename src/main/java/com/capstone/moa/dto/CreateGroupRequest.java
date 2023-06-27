package com.capstone.moa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateGroupRequest {

    private String email;
    private String name;
    private String interest;
    private String introduce;
}
