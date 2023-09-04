package com.capstone.moa.service;

import com.capstone.moa.dto.UploadFileRequest;
import com.capstone.moa.entity.ReportFile;
import com.capstone.moa.repository.ReportFileRepository;
import com.capstone.moa.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ReportFileService {

    private final ReportFileRepository reportFileRepository;

    public String uploadFile(MultipartFile file, UploadFileRequest request) throws IOException {
        reportFileRepository.save(
                ReportFile.builder()
                        .fileName(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .title(request.getTitle())
                        .groupId(request.getGroupId())
                        .fileData(FileUtils.compressFile(file.getBytes()))
                        .build());
        return "redirect:/";
    }
}
