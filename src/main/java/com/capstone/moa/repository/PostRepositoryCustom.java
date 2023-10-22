package com.capstone.moa.repository;

import com.capstone.moa.dto.FindPostsByConditionRequest;
import com.capstone.moa.dto.SearchPostRequest;
import com.capstone.moa.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {


    Page<Post> findAllPostsPage(SearchPostRequest request, Pageable pageable);

    Page<Post> findAllPostsByCondition(FindPostsByConditionRequest request, Pageable pageable);

    Page<Post> findAllByMember(String email, Pageable pageable);
}
