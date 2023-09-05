package com.capstone.moa.dto;

import com.capstone.moa.entity.ReportFile;

public record FindReportFileResponse(
        Long id, String fileName, String title) {

    public static FindReportFileResponse from(ReportFile reportFile) {
        return new FindReportFileResponse(
                reportFile.getId(),
                reportFile.getFileName(),
                reportFile.getTitle()
        );
    }
}