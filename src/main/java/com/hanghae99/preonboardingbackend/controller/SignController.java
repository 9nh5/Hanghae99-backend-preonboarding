package com.hanghae99.preonboardingbackend.controller;

import com.hanghae99.preonboardingbackend.dto.ApiResponse;
import com.hanghae99.preonboardingbackend.dto.SignInRequest;
import com.hanghae99.preonboardingbackend.dto.SignUpRequest;
import com.hanghae99.preonboardingbackend.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class SignController {
    private final SignService signService;

    @PostMapping("/sign-up")
    public ApiResponse signUp(@RequestBody SignUpRequest request) {
        return ApiResponse.success(signService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ApiResponse signIn(@RequestBody SignInRequest request) {
        return ApiResponse.success(signService.signIn(request));
    }
}
