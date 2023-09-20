package com.capstone.moa.controller;

import com.capstone.moa.dto.CreateAddressRequest;
import com.capstone.moa.dto.FindAddressResponse;
import com.capstone.moa.dto.GroupInfoResponse;
import com.capstone.moa.dto.UserDetailsImpl;
import com.capstone.moa.service.AddressService;
import com.capstone.moa.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group/address")
public class AddressMvcController {

    private final AddressService addressService;
    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public String groupAddress(@PathVariable("groupId") Long groupId, Model model) {
        GroupInfoResponse groupInfo = groupService.findGroupInfoById(groupId);
        FindAddressResponse address = addressService.findGroupAddress(groupId);
        model.addAttribute("group", groupInfo);
        model.addAttribute("address", address);
        return "group/group_address";
    }

    @GetMapping("/{groupId}/write")
    public String groupAddressFrom(@PathVariable("groupId") Long groupId, Model model) {
        model.addAttribute("createAddressRequest", new CreateAddressRequest());
        model.addAttribute("groupId", groupId);
        return "group/group_address_write";
    }

    @PostMapping("/save")
    public String saveGroupAddress(CreateAddressRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        addressService.saveGroupAddress(request, userDetails.getUsername());
        return "redirect:/group/intro/" + request.getGroupId();
    }

    @PostMapping("/{addressId}/delete")
    public ResponseEntity<?> deleteGroupAddress(
            @PathVariable("addressId") Long addressId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        if (!addressService.deleteGroupAddress(addressId, userDetails.getMemberId())) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
