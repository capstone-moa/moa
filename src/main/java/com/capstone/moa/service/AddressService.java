package com.capstone.moa.service;

import com.capstone.moa.dto.CreateAddressRequest;
import com.capstone.moa.entity.Address;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.repository.AddressRepository;
import com.capstone.moa.repository.GroupMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final GroupMemberRepository groupMemberRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public void saveGroupAddress(CreateAddressRequest request, String email) {
        System.out.println(request.getGroupId());
        GroupMember leader = groupMemberRepository.findGroupLeader(request.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group Leader not found"));
        if (!leader.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("You don't have authority");
        }
        Address address = Address.builder()
                .zipcode(request.getZipcode())
                .streetAdr(request.getStreetAdr())
                .detailAdr(request.getDetailAdr())
                .group(leader.getGroup()).build();

        leader.getGroup().addAddress(address);
        addressRepository.save(address);
    }
}
