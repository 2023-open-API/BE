package com.pop.planu.domain.controller.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

    private String name;
    private String password;
    private Long studentId;
}
