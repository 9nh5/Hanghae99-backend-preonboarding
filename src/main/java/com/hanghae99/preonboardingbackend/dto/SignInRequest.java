package com.hanghae99.preonboardingbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignInRequest(

        @Schema(description = "회원 이름")
        String username,

        @Schema(description = "회원 비밀번호")
        String password
) {
}
