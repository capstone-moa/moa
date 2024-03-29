package com.capstone.moa.service;

import com.capstone.moa.dto.ModifyCommentRequest;
import com.capstone.moa.dto.WriteCommentRequest;
import com.capstone.moa.entity.Comment;
import com.capstone.moa.entity.Member;
import com.capstone.moa.entity.Post;
import com.capstone.moa.repository.CommentRepository;
import com.capstone.moa.repository.MemberRepository;
import com.capstone.moa.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void writeComment(WriteCommentRequest request, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        commentRepository.save(new Comment(post, member, request.getContent()));
    }

    @Transactional
    public void deleteComment(Long commentId, String email) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.isSameWriter(email)) {
            throw new IllegalArgumentException("You are not the writer of this comment");
        }

        commentRepository.delete(comment);
    }

    /* UPDATE */
    @Transactional
    public void modifyComment(Long postId, ModifyCommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Comment comment = commentRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        comment.modifyComment(request.getContent());
    }
}
