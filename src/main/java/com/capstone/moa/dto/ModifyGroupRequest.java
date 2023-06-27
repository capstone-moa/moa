package com.capstone.moa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModifyGroupRequest {

    private String email;
    private String groupName;
    private String Interest;
    private String introduce;
}
