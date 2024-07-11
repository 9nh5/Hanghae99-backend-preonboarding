package com.hanghae99.preonboardingbackend.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class TokenProvider implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "authorities";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-validity-in-milliseconds}")
    private long tokenValidityInMilliseconds;
    @Value("${jwt.issuer}")
    private String issuer;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte [] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

//    public String createAccessToken(Authentication authentication) {
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//        long now = (new Date()).getTime();
//        Date validity = new Date(now + this.tokenValidityInMilliseconds);
//        return Jwts.builder()
//                .signWith(new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
//                .setSubject(authentication.getName())
//                .claim(AUTHORITIES_KEY, authorities)
//                .setIssuer(issuer)
//                .setExpiration(validity)
//                .compact();
//    }
//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts
//                .parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        Collection<? extends GrantedAuthority> authorities = Arrays.stream(
//                claims.get(AUTHORITIES_KEY).toString().split(",")
//        ).map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//        User principal = new User(claims.getSubject(), "", authorities);
//
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            logger.info("잘못된 JWT 서명입니다.");
//        } catch (ExpiredJwtException e) {
//            logger.info("만료된 JWT 토큰입니다.");
//        } catch (UnsupportedJwtException e) {
//            logger.info("지원되지 않는 JWT 토큰입니다.");
//        } catch (IllegalArgumentException e) {
//            logger.info("JWT 토큰이 잘못되었습니다.");
//        }
//        return false;
//    }
    public String createAccessToken(String userSpecification) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
        return Jwts.builder()
                .signWith(new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setSubject(userSpecification)
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(validity)
                .compact();
    }

    public String validateTokenAndGetSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
