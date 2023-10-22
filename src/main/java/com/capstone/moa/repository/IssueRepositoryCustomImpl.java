package com.capstone.moa.repository;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.Issue;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.capstone.moa.entity.QIssue.issue;

public class IssueRepositoryCustomImpl implements IssueRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public IssueRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Issue> findAllIssueByGroup(Group group, Pageable pageable) {
        List<Issue> content = jpaQueryFactory
                .selectFrom(issue)
                .where(
                        groupEq(group)
                )
                .orderBy(issue.createdDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Issue> countQuery = jpaQueryFactory
                .selectFrom(issue)
                .where(
                        groupEq(group)
                );
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression groupEq(Group group) {
        return ObjectUtils.isEmpty(group) ? issue.group.eq(group) : null;
    }
}
