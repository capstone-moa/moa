package com.capstone.moa.controller.api;

import com.capstone.moa.dto.*;
import com.capstone.moa.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private GroupService groupService;

    @Operation(summary = "그룹 생성")
    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestBody CreateGroupRequest request) {
        String email = "test@email.com";
        groupService.createGroup(request, email);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 정보 수정")
    @PutMapping("/{groupId}")
    public ResponseEntity<Void> modifyGroupInfo(@PathVariable("groupId") Long groupId, @RequestBody ModifyGroupInfoRequest request) {
        groupService.modifyGroupInfo(groupId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 소개 수정")
    @PutMapping("/intro/modify/{groupId}")
    public ResponseEntity<Void> modifyGroupIntro(@PathVariable("groupId") Long groupId, @RequestBody ModifyGroupIntroRequest request) {
        groupService.modifyGroupIntro(groupId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 삭제")
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("groupId") Long groupId, @RequestBody Map<String, String> email) {
        groupService.deleteGroup(groupId, email.get("email"));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 소개 페이지 조회")
    @GetMapping("/{groupId}/intro")
    public ResponseEntity<GroupIntroResponse> findGroupById(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(groupService.findGroupById(groupId));
    }

    @Operation(summary = "멤버 별 가입한 그룹 목록 조회(본인이 리더인 그룹만 조회!!)")
    @GetMapping("/my-page/{memberId}")
    public ResponseEntity<List<FindGroupByLeaderMemberIdResponse>> findGroupsByMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(groupService.findGroupsByLeaderMemberId(memberId));
    }
}
