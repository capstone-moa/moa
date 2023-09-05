package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.ReportFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportFileRepository extends JpaRepository<ReportFile, Long> {

    List<ReportFile> findAllByGroup(Group group);
}
