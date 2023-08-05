package com.capstone.moa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    private String email;
    private String password;
    private String name;
    private String github;
    private String interest;
    private String job;
}
