package com.capstone.moa.repository;

import com.capstone.moa.dto.SearchPostRequest;
import com.capstone.moa.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {


    Page<Post> findAllPostsPage(SearchPostRequest request, Pageable pageable);
}
