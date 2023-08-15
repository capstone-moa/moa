package com.capstone.moa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModifyMemberRequest {

    String name;
    String job;
    String interest;
    String github;
    String introduce;
}
