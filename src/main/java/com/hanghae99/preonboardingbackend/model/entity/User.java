package com.hanghae99.preonboardingbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae99.preonboardingbackend.dto.AuthorityDto;
import com.hanghae99.preonboardingbackend.dto.SignUpRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // primary key
    // 자동 증가 되는
    @Id
    @Column(name = "user_id")
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 100)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthorityDto authority;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "activated")
    @JsonIgnore
    private boolean activated;

//    @ManyToMany
//    @JoinTable(
//        name = "user_authority",
//        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
//        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
//    private Set<Authority> authorities;

    public static User from(SignUpRequest request, PasswordEncoder encoder) {
        return User.builder()
                .username(request.username())
                .password(encoder.encode(request.password()))
                .nickname(request.nickname())
                .authority(AuthorityDto.USER)
                .build();
    }

    @Builder
    private User(String username, String password, String nickname, AuthorityDto authority) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
    }
}
