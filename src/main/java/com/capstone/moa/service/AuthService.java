package com.capstone.moa.service;

import com.capstone.moa.dto.JoinRequest;
import com.capstone.moa.entity.Member;
import com.capstone.moa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(JoinRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일 입니다.");
        }
        Member member = memberRepository.save(request.toEntity());
        return member.getId();
    }
}
