package com.capstone.moa.dto;

import com.capstone.moa.entity.Member;

public record FindMemberByIdResponse(
        Long id, String name, String email, String interest, String job
) {
    public static FindMemberByIdResponse from(Member member) {
        return new FindMemberByIdResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getInterest().name(),
                member.getJob().name()
        );
    }
}
