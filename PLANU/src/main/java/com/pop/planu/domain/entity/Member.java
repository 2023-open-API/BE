package com.pop.planu.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String name;

    private Long studentId;

    private String password;


    public boolean matchPassword(String password){
        if( this.getPassword().equals(password)) return true;
        return false;
    }
}
