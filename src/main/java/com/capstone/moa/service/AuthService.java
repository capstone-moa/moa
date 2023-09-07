package com.capstone.moa.service;

import com.capstone.moa.dto.JoinRequest;
import com.capstone.moa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    @Transactional
    public void join(JoinRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일 입니다.");
        }
        memberRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public int checkEmailDuplication(String email) {
        if (memberRepository.existsByEmail(email)) {
            return 1;
        }
        return 0;
    }
}
