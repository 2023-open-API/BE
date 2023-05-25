package com.pop.planu.domain.controller;

import com.pop.planu.domain.controller.request.member.LoginRequest;
import com.pop.planu.domain.controller.request.member.SignUpRequest;
import com.pop.planu.domain.service.MemberService;
import com.pop.planu.global.dto.TokenDto;
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
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(memberService.login(loginRequest.getStudentId(),loginRequest.getPassword()));
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest){
        Long studentId = memberService.signUp(signUpRequest);

        URI uri = fromPath("/member/{memberId}") // api/member/{studentId}
                .buildAndExpand(studentId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }


}
