package com.capstone.moa.controller;

import com.capstone.moa.dto.*;
import com.capstone.moa.service.GroupProfileService;
import com.capstone.moa.service.GroupService;
import com.capstone.moa.service.IssueService;
import com.capstone.moa.service.ReportFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group/project")
public class ProjectMvcController {

    private final GroupService groupService;
    private final IssueService issueService;
    private final ReportFileService reportFileService;
    private final GroupProfileService groupProfileService;


    @GetMapping("/{groupId}/issue")
    public String findProject(
            @PathVariable("groupId") Long groupId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model
    ) throws IOException {
        Pageable pageable = PageRequest.of(page-1, 5);
        GroupInfoResponse groupInfo = groupService.findGroupInfoById(groupId);
        Page<FindIssueResponse> issueList = issueService.findIssueList(groupId, pageable);
        String groupProfile = groupProfileService.downloadImage(groupId);

        model.addAttribute("groupProfile", groupProfile);
        model.addAttribute("group", groupInfo);
        model.addAttribute("issueList", issueList);

        boolean check = false;
        if (userDetails != null) {
            check = groupService.checkIsGroupMember(groupId, userDetails.getMemberId());
        }
        model.addAttribute("check", check);

        return "group/group_management_issue";
    }

    @GetMapping("/{groupId}/issue/write")
    public String issueForm(@PathVariable("groupId") Long groupId, Model model) {
        model.addAttribute("WriteIssueRequest", new WriteIssueRequest());
        model.addAttribute("groupId", groupId);
        return "group/group_issue_write";
    }

    @PostMapping("/issue/save")
    public String saveIssue(WriteIssueRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        issueService.writeIssue(request, userDetails.getMemberId());
        return "redirect:/";
    }

    @GetMapping("/{groupId}/issue/{issueId}")
    public String findIssue(
            @PathVariable("groupId") Long groupId,
            @PathVariable("issueId") Long issueId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model) {
        FindIssueResponse issue = issueService.findIssueById(issueId);
        model.addAttribute("issue", issue);
        model.addAttribute("groupId", groupId);
        boolean check = false;
        if (userDetails != null) {
            check = groupService.checkIsGroupMember(groupId, userDetails.getMemberId());
        }
        model.addAttribute("check", check);
        return "group/group_issue_view";
    }

    @PostMapping("/issue/{issueId}/delete")
    public String deleteIssue(@PathVariable("issueId") Long issueId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        issueService.deleteIssue(issueId, userDetails.getMemberId());
        return "redirect:/";
    }

    @GetMapping("/{groupId}/files")
    public String findFiles(@PathVariable("groupId") Long groupId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        GroupInfoResponse groupInfo = groupService.findGroupInfoById(groupId);
        List<FindReportFileResponse> reportFiles = reportFileService.findReportFilesByGroupId(groupId);
        boolean check = false;
        if (userDetails != null) {
            check = groupService.checkIsGroupMember(groupId, userDetails.getMemberId());
        }
        String groupProfile = groupProfileService.downloadImage(groupId);
        model.addAttribute("groupProfile", groupProfile);

        model.addAttribute("group", groupInfo);
        model.addAttribute("reportFiles", reportFiles);
        model.addAttribute("check", check);
        return "group/group_management_file";
    }

    @GetMapping("/{groupId}/files/write")
    public String fileForm(@PathVariable("groupId") Long groupId, Model model) {
        model.addAttribute("groupId", groupId);
        return "group/group_file_write";
    }

    @PostMapping(value = "/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadFile(@RequestPart(value = "file") MultipartFile file,
                             @RequestPart(value = "uploadFileRequest") UploadFileRequest request) throws IOException {
        return reportFileService.uploadFile(file, request);
    }

    @GetMapping("/download/{reportFileId}")
    public ResponseEntity<?> download(@PathVariable("reportFileId") Long reportFileId) {
        DownLoadFileResponse downloadFile = reportFileService.downloadFile(reportFileId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(downloadFile.fileName(), StandardCharsets.UTF_8)
                .build());
        headers.set(HttpHeaders.CONTENT_TYPE, downloadFile.contentType());
        return ResponseEntity.ok()
                .headers(headers)
                .body(downloadFile.fileData());
    }

    @PostMapping("/{groupId}/files/{reportFileId}/delete")
    public ResponseEntity<?> deleteReportFile(
            @PathVariable("groupId") Long groupId,
            @PathVariable("reportFileId") Long reportFileId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean check = groupService.checkIsGroupMember(groupId, userDetails.getMemberId());
        if (check) {
            reportFileService.deleteReportFile(reportFileId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
