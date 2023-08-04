package com.capstone.moa.service;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.Member;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.entity.enums.Job;
import com.capstone.moa.entity.enums.Role;
import com.capstone.moa.repository.MemberRepository;
import com.capstone.moa.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public void signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입한 회원입니다.");
        }

        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .github(request.getGithub())
                .role(Role.USER)
                .interest(Interest.find(request.getInterest()))
                .job(Job.find(request.getJob()))
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member Not Found"));
        String token = jwtService.generateToken(new UserDetailsImpl(member));
        return TokenResponse.of(token);
    }
}
