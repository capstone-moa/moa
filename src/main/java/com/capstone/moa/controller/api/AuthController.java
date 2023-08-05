package com.capstone.moa.controller.api;

import com.capstone.moa.dto.LoginRequest;
import com.capstone.moa.dto.JoinRequest;
import com.capstone.moa.dto.TokenResponse;
import com.capstone.moa.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequest request) {
        authService.join(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
