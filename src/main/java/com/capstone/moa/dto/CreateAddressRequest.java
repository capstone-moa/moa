package com.capstone.moa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateAddressRequest {

    private Long groupId;
    private String zipcode;
    private String streetAdr;
    private String detailAdr;
}
