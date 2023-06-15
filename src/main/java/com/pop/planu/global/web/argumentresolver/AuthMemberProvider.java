package com.pop.planu.global.web.argumentresolver;

import com.pop.planu.domain.auth.AuthMember;
import com.pop.planu.domain.auth.jwt.AuthToken;
import com.pop.planu.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Base64;

@RequiredArgsConstructor
@Component
public class AuthMemberProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    // secret key를 base64로 인코딩
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public AuthMember getAuthMember(AuthToken authToken) {
        Claims body = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(authToken.getToken()).getBody();
        return AuthMember.builder()
                .id(body.get("id", Long.class))
                .studentId(body.get("studentId", Long.class))
                .build();
    }
}
