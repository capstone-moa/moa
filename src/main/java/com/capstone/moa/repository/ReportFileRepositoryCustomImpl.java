package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.QReportFile;
import com.capstone.moa.entity.ReportFile;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.capstone.moa.entity.QIssue.issue;
import static com.capstone.moa.entity.QReportFile.reportFile;

public class ReportFileRepositoryCustomImpl implements ReportFileRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ReportFileRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<ReportFile> findAllReportFileByGroup(Group group, Pageable pageable) {
        List<ReportFile> content = jpaQueryFactory
                .selectFrom(reportFile)
                .where(
                        groupEq(group)
                )
                .orderBy(reportFile.createdDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<ReportFile> countQuery = jpaQueryFactory
                .selectFrom(reportFile)
                .where(
                        groupEq(group)
                );
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression groupEq(Group group) {
        return ObjectUtils.isEmpty(group) ? issue.group.eq(group) : null;
    }

}
