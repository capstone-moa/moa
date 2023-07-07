package com.capstone.moa.service;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.Interest;
import com.capstone.moa.entity.Member;
import com.capstone.moa.entity.Post;
import com.capstone.moa.entity.PostType;
import com.capstone.moa.repository.MemberRepository;
import com.capstone.moa.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void writePost(WritePostRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        postRepository.save(new Post(member, request.getTitle(), request.getContent(), request.getInterest(), request.getPostType()));
    }

    @Transactional(readOnly = true)
    public FindPostsResponse findAllPosts() {
        List<FindPostResponse> posts = postRepository.findAll()
                .stream()
                .map(FindPostResponse::from)
                .toList();

        return new FindPostsResponse(posts);
    }

    @Transactional(readOnly = true)
    public FindPostsByInterestResponse findPostsByInterest(String interest) {
        Interest selectedInterest = Interest.find(interest);
        List<FindPostByInterestResponse> posts = postRepository.findAllByInterest(selectedInterest)
                .stream()
                .map(FindPostByInterestResponse::from)
                .toList();

        return new FindPostsByInterestResponse(selectedInterest, posts);
    }

    @Transactional(readOnly = true)
    public FindPostsResponse findPostsByPostType(String postType) {
        PostType selectedPostType = PostType.find(postType);
        List<FindPostResponse> posts = postRepository.findAllByPostType(selectedPostType)
                .stream()
                .map(FindPostResponse::from)
                .toList();
        return new FindPostsResponse(posts);
    }

    @Transactional(readOnly = true)
    public FindPostsResponse findPostsByPostTypeAndInterest(String postType, String interest) {
        PostType selectedPostType = PostType.find(postType);
        Interest selectedInterest = Interest.find(interest);
        List<FindPostResponse> posts = postRepository.findAllByPostTypeAndInterest(selectedPostType, selectedInterest)
                .stream()
                .map(FindPostResponse::from)
                .toList();
        return new FindPostsResponse(posts);
    }

    @Transactional(readOnly = true)
    public FindPostResponse findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return FindPostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public FindPostWithCommentsResponse findPostWithCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return FindPostWithCommentsResponse.from(post);
    }

    @Transactional
    public void modifyPost(Long postId, ModifyPostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.isSameWriter(request.getEmail())) {
            throw new IllegalArgumentException("You are not the writer of this post");
        }

        post.modify(request.getTitle(), request.getContent(), request.getInterest());
    }

    @Transactional
    public void deletePost(Long postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.isSameWriter(email)) {
            throw new IllegalArgumentException("You are not the writer of this post");
        }

        postRepository.delete(post);
    }
}
