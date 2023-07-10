package com.capstone.moa.controller;

import com.capstone.moa.dto.CreateGroupRequest;
import com.capstone.moa.dto.GroupIntroResponse;
import com.capstone.moa.dto.ModifyGroupInfoRequest;
import com.capstone.moa.dto.ModifyGroupIntroRequest;
import com.capstone.moa.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private GroupService groupService;

    @Operation(summary = "그룹 생성")
    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestBody CreateGroupRequest request) {
        groupService.createGroup(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 정보 수정")
    @PutMapping("/{groupId}")
    public ResponseEntity<Void> modifyGroupInfo(@PathVariable("groupId") Long groupId, @RequestBody ModifyGroupInfoRequest request) {
        groupService.modifyGroupInfo(groupId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 소개 수정")
    @PutMapping("/{groupId}/intro")
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
}
