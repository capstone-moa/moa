package com.capstone.moa.repository;

import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.entity.Member;
import com.capstone.moa.entity.Post;
import com.capstone.moa.entity.enums.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct t from Post t join fetch t.member where t.postType = ?1")
    List<Post> findAllByTypeFetch(PostType postType);
    List<Post> findAllByInterest(Interest interest);
    List<Post> findAllByPostType(PostType postType);
    List<Post> findAllByPostTypeAndInterest(PostType postType, Interest interest);
    List<Post> findAllByMember(Member member);
}
