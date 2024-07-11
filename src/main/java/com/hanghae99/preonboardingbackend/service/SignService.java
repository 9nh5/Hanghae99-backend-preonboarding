package com.hanghae99.preonboardingbackend.service;

import com.hanghae99.preonboardingbackend.config.jwt.TokenProvider;
import com.hanghae99.preonboardingbackend.dto.SignInRequest;
import com.hanghae99.preonboardingbackend.dto.SignInResponse;
import com.hanghae99.preonboardingbackend.dto.SignUpRequest;
import com.hanghae99.preonboardingbackend.dto.SignUpResponse;
import com.hanghae99.preonboardingbackend.model.entity.User;
import com.hanghae99.preonboardingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SignService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder encoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        User user = userRepository.save(User.from(request, encoder));
        try {
            userRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 사용중인 이름입니다.");
        }
        return SignUpResponse.from(user);
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest request) {
        User user = userRepository.findByUsername(request.username())
                .filter(it -> encoder.matches(request.password(), it.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("사용자 이름 혹은 비밀번호가 일치하지 않습니다."));
        String token = tokenProvider.createAccessToken(String.format("%s:%s", user.getUserId(), user.getAuthority()));
        return new SignInResponse(user.getNickname(), user.getAuthority(), token);
    }
}
