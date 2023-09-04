package com.capstone.moa.service;

import com.capstone.moa.dto.DownLoadFileResponse;
import com.capstone.moa.dto.FindReportFileResponse;
import com.capstone.moa.dto.UploadFileRequest;
import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.ReportFile;
import com.capstone.moa.repository.GroupRepository;
import com.capstone.moa.repository.ReportFileRepository;
import com.capstone.moa.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportFileService {

    private final ReportFileRepository reportFileRepository;
    private final GroupRepository groupRepository;

    public String uploadFile(MultipartFile file, UploadFileRequest request) throws IOException {
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        reportFileRepository.save(
                ReportFile.builder()
                        .fileName(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .title(request.getTitle())
                        .group(group)
                        .fileData(FileUtils.compressFile(file.getBytes()))
                        .build());
        return "redirect:/";
    }

    public List<FindReportFileResponse> findReportFilesByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        return reportFileRepository.findAllByGroupId(groupId)
                .stream()
                .map(FindReportFileResponse::from)
                .toList();
    }

    public DownLoadFileResponse downloadFile(Long reportFileId) {
        ReportFile reportFile = reportFileRepository.findById(reportFileId)
                .orElseThrow(() -> new IllegalArgumentException("ReportFile not found"));

        return DownLoadFileResponse.from(reportFile);
    }
}
