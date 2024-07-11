package com.hanghae99.preonboardingbackend.repository;

import com.hanghae99.preonboardingbackend.dto.AuthorityDto;
import com.hanghae99.preonboardingbackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    List<User> findAllByAuthority(AuthorityDto authority);
}
