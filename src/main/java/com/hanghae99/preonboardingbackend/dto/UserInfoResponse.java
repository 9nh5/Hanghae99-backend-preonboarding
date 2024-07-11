package com.hanghae99.preonboardingbackend.dto;

import com.hanghae99.preonboardingbackend.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UserInfoResponse(

        @Schema(description = "회원 이름")
        String username,
        @Schema(description = "회원 비밀번호")
        String password,
        @Schema(description = "회원 닉네임")
        String nickname,
        @Schema(description = "회원 유형")
        AuthorityDto authority
) {
        public static UserInfoResponse from(User user) {
                return new UserInfoResponse(
                        user.getUsername(),
                        user.getPassword(),
                        user.getNickname(),
                        user.getAuthority()
                );
        }
}
