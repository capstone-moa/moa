package com.capstone.moa.controller;

import com.capstone.moa.dto.InviteGroupRequest;
import com.capstone.moa.service.InviteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class InviteController {

    private final InviteService inviteService;

    @Operation(summary = "그룹 초대", description = "email로 회원을 검색해 그룹에 초대한다")
    @PostMapping("/invite")
    public ResponseEntity<Void> inviteToGroup(@RequestBody InviteGroupRequest request) {
        inviteService.inviteToGroup(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 초대 요청 수락")
    @PostMapping("/my-page/{memberId}/group/{inviteId}")
    public ResponseEntity<Void> acceptInvite(
            @PathVariable("memberId") Long memberId,
            @PathVariable("inviteId") Long inviteId) {
        inviteService.acceptInvite(memberId, inviteId);
        return ResponseEntity.noContent().build();
    }
}
