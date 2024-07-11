package com.hanghae99.preonboardingbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignInResponse(
        @Schema(description = "회원 닉네임")
        String nickname,
        AuthorityDto authority,
        String token
) {
}
