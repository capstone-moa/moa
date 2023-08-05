package com.capstone.moa.controller;

import com.capstone.moa.dto.LoginRequest;
import com.capstone.moa.dto.JoinRequest;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.entity.enums.Job;
import com.capstone.moa.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthMvcController {

    private final AuthService authService;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("joinRequest", new JoinRequest());
        return "auth/join";
    }

    @PostMapping("/join")
    public String doJoin(JoinRequest request) {
        authService.join(request);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    @ModelAttribute("interests")
    private Interest[] putInterest() {
        return Interest.values();
    }

    @ModelAttribute("jobs")
    private Job[] putJob() {
        return Job.values();
    }
}
