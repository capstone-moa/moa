package com.capstone.moa.service;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.Interest;
import com.capstone.moa.entity.Member;
import com.capstone.moa.entity.Post;
import com.capstone.moa.repository.MemberRepository;
import com.capstone.moa.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void writePost(WritePostRequest request) {
        Member member = memberRepository.findByMemberId(request.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        postRepository.save(new Post(member, request.getTitle(), request.getContent(), request.getInterest()));
    }

    public FindPostsResponse findAllPosts() {
        List<FindPostResponse> posts = postRepository.findAll()
            .stream()
            .map(FindPostResponse::from)
            .toList();

        return new FindPostsResponse(posts);
    }

    public FindPostsByInterestResponse findPostsByInterest(String interest) {
        Interest selectedInterest = Interest.find(interest);
        List<FindPostByInterestResponse> posts = postRepository.findAllByInterest(selectedInterest)
            .stream()
            .map(FindPostByInterestResponse::from)
            .toList();

        return new FindPostsByInterestResponse(selectedInterest, posts);
    }

    public FindPostResponse findPostById(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return FindPostResponse.from(post);
    }

    public void modifyPost(Long postId, ModifyPostRequest request) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.isSameWriter(request.getMemberId())) {
            throw new IllegalArgumentException("You are not the writer of this post");
        }

        post.modify(request.getTitle(), request.getContent(), request.getInterest());
    }
}
