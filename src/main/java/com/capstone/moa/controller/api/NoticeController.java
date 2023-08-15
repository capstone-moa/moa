package com.capstone.moa.controller.api;

import com.capstone.moa.dto.WriteNoticeRequest;
import com.capstone.moa.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/group/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "그룹 공지 등록")
    @PostMapping("/{groupId}")
    public ResponseEntity<Void> writeNotice(@RequestBody WriteNoticeRequest request, @PathVariable Long groupId) {
        String email = "test@email.com";
        noticeService.createNotice(request, email);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 공지 삭제")
    @DeleteMapping("/{noticeId}/delete")
    public ResponseEntity<Void> deleteNotice(@PathVariable(name = "noticeId") Long noticeId) {
        String email = "test@email.com";
        noticeService.deleteNotice(noticeId,email);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 공지 리스트 조회")
    @GetMapping("/{groupId}")
    public ResponseEntity<?> findNoticesByGroupId(@PathVariable(name = "groupId") Long groupId) {
        return ResponseEntity.ok(noticeService.findNoticesByGroupId(groupId));
    }
}
