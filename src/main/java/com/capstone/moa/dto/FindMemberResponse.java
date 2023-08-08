package com.capstone.moa.dto;

import com.capstone.moa.entity.Member;

public record FindMemberResponse(
        Long id, String name, String email, String interest, String job
) {
    public static FindMemberResponse from(Member member) {
        return new FindMemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getInterest().name(),
                member.getJob().name()
        );
    }
}
