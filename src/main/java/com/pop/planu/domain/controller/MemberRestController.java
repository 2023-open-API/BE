package com.pop.planu.domain.controller;

import com.pop.planu.domain.controller.request.member.LoginRequest;
import com.pop.planu.domain.controller.request.member.SignUpRequest;
import com.pop.planu.domain.service.MemberService;
import com.pop.planu.global.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "사용자", description = "회원가입 로그인 관련 API")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 여부를 확인한다.")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(memberService.login(loginRequest.getStudentId(),loginRequest.getPassword()));
    }

    @PostMapping("/signUp")
    @Operation(summary = "회원가입", description = "회원가입 진행, 사용자 저장")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest){
        Long studentId = memberService.signUp(signUpRequest);

        URI uri = fromPath("/member/{memberId}") // api/member/{studentId}
                .buildAndExpand(studentId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }


}
