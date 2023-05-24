package com.pop.planu.domain.service;


import com.pop.planu.domain.controller.request.SignUpRequest;
import com.pop.planu.domain.repository.MemberRepository;
import com.pop.planu.global.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.pop.planu.domain.entity.Member;
import com.pop.planu.global.dto.TokenDto;
import com.pop.planu.global.exception.PasswordMismatchException;
import com.pop.planu.global.exception.StudentIdDuplicatedException;
import com.pop.planu.global.exception.StudentIdFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;
    public Long signUp(SignUpRequest signUpRequest){
        checkDuplicateStudentId(signUpRequest.getStudentId());

        Member member = Member.builder()
                .name(signUpRequest.getName())
                .studentId(signUpRequest.getStudentId())
                .password(signUpRequest.getPassword()).build();

        return memberRepository.save(member).getStudentId();
    }

    // 학번 중복 확인 여부
    private void checkDuplicateStudentId(Long studentId){
        if(memberRepository.findByStudentId(studentId).isPresent()){
            throw new StudentIdDuplicatedException("이미 존재하는 사용자입니다.");
        }
    }

    public TokenDto login(Long studentId, String password) {
        Member findMember = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new StudentIdFoundException("존재하지 않는 학번 입니다."));
        if(!findMember.matchPassword(password)){
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        return TokenDto.builder().token(jwtTokenProvider.createToken(findMember)).build();
    }

}
