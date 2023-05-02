package com.capstone.moa.service;

import com.capstone.moa.dto.FindPostResponse;
import com.capstone.moa.dto.FindPostsResponse;
import com.capstone.moa.dto.WritePostRequest;
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

    public void writePost(WritePostRequest writePostRequest) {
        Member member = memberRepository.findByMemberId(writePostRequest.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        postRepository.save(new Post(member, writePostRequest.getTitle(), writePostRequest.getContent(), writePostRequest.getInterest()));
    }

    public FindPostsResponse findAllPosts() {
        List<FindPostResponse> posts = postRepository.findAll().stream()
            .map(FindPostResponse::from)
            .toList();

        return new FindPostsResponse(posts);
    }
}
