package com.pop.planu.member.domain.controller.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    private Long studentId;
    private String password;

}

