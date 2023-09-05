package com.capstone.moa.dto;

import com.capstone.moa.entity.ReportFile;
import com.capstone.moa.utils.FileUtils;

public record DownLoadFileResponse(
        byte[] fileData, String contentType, String fileName
) {
    public static DownLoadFileResponse from(ReportFile reportFile) {
        return new DownLoadFileResponse(
                FileUtils.decompressFile(reportFile.getFileData()),
                reportFile.getContentType(),
                reportFile.getFileName()
        );
    }
}