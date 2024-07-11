package com.hanghae99.preonboardingbackend.dto;

import com.hanghae99.preonboardingbackend.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record SignUpResponse(
        @Schema(description = "회원 고유키")
        UUID id,
        @Schema(description = "회원 이름")
        String username

) {
    public static SignUpResponse from(User user) {
        return new SignUpResponse(
                user.getUserId(),
                user.getUsername()
        );
    }
}
