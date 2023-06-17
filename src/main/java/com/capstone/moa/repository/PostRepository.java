package com.capstone.moa.repository;

import com.capstone.moa.entity.Interest;
import com.capstone.moa.entity.Post;
import com.capstone.moa.entity.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByInterest(Interest interest);

    List<Post> findAllByPostType(PostType postType);
}
