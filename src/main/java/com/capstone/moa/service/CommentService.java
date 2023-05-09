package com.capstone.moa.service;

import com.capstone.moa.dto.WriteCommentRequest;
import com.capstone.moa.entity.Comment;
import com.capstone.moa.entity.Member;
import com.capstone.moa.entity.Post;
import com.capstone.moa.repository.CommentRepository;
import com.capstone.moa.repository.MemberRepository;
import com.capstone.moa.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void writeComment(Long postId, WriteCommentRequest request) {
        Member member = memberRepository.findByMemberId(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        commentRepository.save(new Comment(post, member, request.getContent()));
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, String memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.isSameWriter(memberId)) {
            throw new IllegalArgumentException("You are not the writer of this comment");
        }

        commentRepository.delete(comment);
    }
}
