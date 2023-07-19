package com.capstone.moa.controller;

import com.capstone.moa.dto.WriteNoticeRequest;
import com.capstone.moa.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/group/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "그룹 공지 등록")
    @PostMapping
    public ResponseEntity<Void> writeNotice(@RequestBody WriteNoticeRequest request) {
        noticeService.createNotice(request);
        return ResponseEntity.noContent().build();
    }
}
