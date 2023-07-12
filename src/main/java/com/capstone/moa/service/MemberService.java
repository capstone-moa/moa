package com.capstone.moa.service;

import com.capstone.moa.dto.FindMemberByIdResponse;
import com.capstone.moa.entity.Member;
import com.capstone.moa.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public FindMemberByIdResponse findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return FindMemberByIdResponse.from(member);
    }
}
