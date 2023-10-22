package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueRepositoryCustom {

    Page<Issue> findAllIssueByGroup(Group group, Pageable pageable);
}
