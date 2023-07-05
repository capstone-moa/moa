package com.capstone.moa.controller;

import com.capstone.moa.dto.FindInvitationResponse;
import com.capstone.moa.dto.InviteGroupRequest;
import com.capstone.moa.service.InvitationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class InvitationController {

    private final InvitationService invitationService;

    @Operation(summary = "그룹 초대", description = "email로 회원을 검색해 그룹에 초대한다")
    @PostMapping("/invite")
    public ResponseEntity<Void> inviteToGroup(@RequestBody InviteGroupRequest request) {
        invitationService.inviteToGroup(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 초대 요청 수락")
    @PatchMapping("/my-page/{memberId}/group/{inviteId}/accept")
    public ResponseEntity<Void> acceptInvite(
            @PathVariable("memberId") Long memberId,
            @PathVariable("inviteId") Long inviteId) {
        invitationService.acceptInvite(memberId, inviteId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 초대 요청 거절")
    @PatchMapping("/my-page/{memberId}/group/{inviteId}/reject")
    public ResponseEntity<Void> rejectInvite(
            @PathVariable("memberId") Long memberId,
            @PathVariable("inviteId") Long inviteId) {
        invitationService.rejectInvite(memberId, inviteId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원이 받은 초대 요청 목록 조회")
    @GetMapping("/my-page/{memberId}/group")
    public ResponseEntity<List<FindInvitationResponse>> findInvitationsByMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(invitationService.findInvitationsByMember(memberId));
    }
}
