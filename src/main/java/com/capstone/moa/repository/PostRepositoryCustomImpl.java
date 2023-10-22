package com.capstone.moa.repository;

import com.capstone.moa.dto.FindPostsByConditionRequest;
import com.capstone.moa.dto.SearchPostRequest;
import com.capstone.moa.entity.Post;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.entity.enums.PostType;
import com.capstone.moa.entity.enums.Region;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.capstone.moa.entity.QPost.post;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Post> findAllPostsPage(SearchPostRequest request, Pageable pageable) {

        List<Post> content = jpaQueryFactory
                .selectFrom(post)
                .where(
                        titleEq(request.getTitle()),
                        postTypeEq(request.getPostType())
                )
                .orderBy(post.createdDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = jpaQueryFactory
                .selectFrom(post)
                .where(titleEq(request.getTitle()),
                        postTypeEq(request.getPostType())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<Post> findAllPostsByCondition(FindPostsByConditionRequest request, Pageable pageable) {
        List<Post> content = jpaQueryFactory
                .selectFrom(post)
                .where(
                        postTypeEq(request.getPostType()),
                        interestEq(request.getInterest()),
                        regionEq(request.getRegion())
                )
                .orderBy(post.createdDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = jpaQueryFactory
                .selectFrom(post)
                .where(
                        postTypeEq(request.getPostType()),
                        interestEq(request.getInterest()),
                        regionEq(request.getRegion())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<Post> findAllByMember(String email, Pageable pageable) {
        List<Post> content = jpaQueryFactory
                .selectFrom(post)
                .where(
                        memberEq(email)
                )
                .orderBy(post.createdDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = jpaQueryFactory
                .selectFrom(post)
                .where(
                        memberEq(email)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression titleEq(String title) {
        return StringUtils.hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression postTypeEq(String postType) {
        return StringUtils.hasText(postType) ? post.postType.eq(PostType.find(postType)) : null;
    }

    private BooleanExpression interestEq(String interest) {
        return StringUtils.hasText(interest) ? post.interest.eq(Interest.find(interest)) : null;
    }

    private BooleanExpression regionEq(String region) {
        return StringUtils.hasText(region) ? post.region.eq(Region.fromKrName(region)) : null;
    }

    private BooleanExpression memberEq(String email) {
        return StringUtils.hasText(email) ? post.member.email.eq(email) : null;
    }
}
