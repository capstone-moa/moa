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
        linkService.addGithubLink(groupId, githubLink.get("githubLink"));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "figma 링크 등록")
    @PostMapping("/figma/{groupId}")
    public ResponseEntity<Void> addFigmaLink(@PathVariable(name = "groupId") Long groupId, @RequestBody Map<String, String> figmaLink) {
        linkService.addFigmaLink(groupId, figmaLink.get("figmaLink"));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "erdCloud 링크 등록")
    @PostMapping("/erd-cloud/{groupId}")
    public ResponseEntity<Void> addErdCloudLink(@PathVariable(name = "groupId") Long groupId, @RequestBody Map<String, String> erdCloudLink) {
        linkService.addErdCloudLink(groupId, erdCloudLink.get("erdCloudLink"));
        return ResponseEntity.noContent().build();
    }
}
