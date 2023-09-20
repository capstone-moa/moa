package com.capstone.moa.service;

import com.capstone.moa.dto.CreateAddressRequest;
import com.capstone.moa.dto.FindAddressResponse;
import com.capstone.moa.entity.Address;
import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.repository.AddressRepository;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final GroupMemberRepository groupMemberRepository;
    private final AddressRepository addressRepository;
    private final GroupRepository groupRepository;

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

    @Transactional(readOnly = true)
    public FindAddressResponse findGroupAddress(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        if (!isAddressSaved(groupId)) {
            return null;
        }
        Address address = addressRepository.findByGroup(group)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        return FindAddressResponse.from(address);
    }

    @Transactional
    public boolean deleteGroupAddress(Long addressId, Long memberId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        if (!groupMemberRepository.existsGroupMemberByGroupIdAndMemberId(address.getGroup().getId(), memberId)) {
            return false;
        }
        addressRepository.delete(address);
        return true;
    }

    private boolean isAddressSaved(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        return addressRepository.existsByGroup(group);
    }
}
