package com.capstone.moa.controller.api;

import com.capstone.moa.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LinkController {

    private final LinkService linkService;

    @Operation(summary = "github 링크 등록")
    @PostMapping("/github/{groupId}")
    public ResponseEntity<Void> addGithubLink(@PathVariable(name = "groupId") Long groupId, @RequestBody Map<String, String> githubLink) {
        linkService.modifyGroupIntroLink(groupId, githubLink.get("githubLink"), "대표 링크");
        return ResponseEntity.noContent().build();
    }
}
