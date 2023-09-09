package com.capstone.moa.dto;

import com.capstone.moa.entity.Address;

public record FindAddressResponse(
        Long addressId, String zipcode, String streetAdr, String detailAdr) {
    public static FindAddressResponse from(Address address) {
        return new FindAddressResponse(
                address.getId(),
                address.getZipcode(),
                address.getStreetAdr(),
                address.getDetailAdr()
        );
    }
}

