package com.capstone.moa.service;

import com.capstone.moa.dto.FindMemberResponse;
import com.capstone.moa.entity.Member;
import com.capstone.moa.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public FindMemberResponse findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return FindMemberResponse.from(member);
    }

    @Transactional
    public FindMemberResponse findMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        log.info("email : {}", email);
        return FindMemberResponse.from(member);
    }
}
