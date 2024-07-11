package com.hanghae99.preonboardingbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpRequest(
        @Schema(description = "회원 아이디")
        String userId,
        @Schema(description = "회원 비밀번호")
        String password,
        @Schema(description = "회원 이름")
        String username,

        @Schema(description = "회원 닉네임")
        String nickname
) {
}
