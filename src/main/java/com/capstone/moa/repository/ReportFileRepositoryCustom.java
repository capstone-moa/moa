package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.ReportFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportFileRepositoryCustom {

    Page<ReportFile> findAllReportFileByGroup(Group group, Pageable pageable);
}
