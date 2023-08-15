package com.capstone.moa.dto;

import com.capstone.moa.entity.Member;

public record FindMemberInfoResponse(
        Long id, String name, String email, String interest, String job,
        String github, String introduce
) {
    public static FindMemberInfoResponse from(Member member) {
        return new FindMemberInfoResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getInterest().name(),
                member.getJob().name(),
                member.getGithub(),
                member.getIntroduce()
        );
    }
}
