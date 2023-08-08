package com.capstone.moa.dto;

import com.capstone.moa.entity.Member;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.entity.enums.Job;
import com.capstone.moa.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    private String email;
    private String password;
    private String name;
    private String github;
    private String interest;
    private String job;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .github(github)
                .role(Role.USER)
                .interest(Interest.find(interest))
                .job(Job.find(job))
                .build();
    }
}
