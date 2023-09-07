package com.capstone.moa.controller;

import com.capstone.moa.dto.JoinRequest;
import com.capstone.moa.entity.enums.Interest;
import com.capstone.moa.entity.enums.Job;
import com.capstone.moa.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
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
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model){
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/posts";
    }

    @GetMapping("/check-email")
    @ResponseBody
    public int checkEmailDuplication(String email) {
        System.out.println("email : " + email);
        return authService.checkEmailDuplication(email);
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
