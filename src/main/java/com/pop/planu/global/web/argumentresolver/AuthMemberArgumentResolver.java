package com.pop.planu.global.web.argumentresolver;

import com.pop.planu.domain.auth.AuthMember;
import com.pop.planu.domain.auth.jwt.AuthToken;
import com.pop.planu.global.exception.FailedAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private String AUTH_TOKEN_HEADER_NAME = "Authorization";
    private String AUTH_TOKEN_HEADER_PREFIX = "Bearer ";

    private final AuthMemberProvider authMemberProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return  parameter.getParameterAnnotation(Auth.class)!= null
                && parameter.getParameterType().equals(AuthMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authTokenValue = webRequest.getHeader(AUTH_TOKEN_HEADER_NAME);

        if(!authTokenValue.startsWith(AUTH_TOKEN_HEADER_PREFIX)) {
            throw new FailedAuthenticationException("Auth Token이 없어 인증할 수 없습니다.");
        }

        String authToken = authTokenValue.replace(AUTH_TOKEN_HEADER_PREFIX, "").trim();

        AuthMember authMember = authMemberProvider.getAuthMember(AuthToken.builder().token(authToken).build());

        return authMember;
    }
}
