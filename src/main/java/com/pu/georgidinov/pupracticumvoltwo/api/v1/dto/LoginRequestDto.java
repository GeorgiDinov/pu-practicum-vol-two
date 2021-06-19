package com.pu.georgidinov.pupracticumvoltwo.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    private String email;
    private String password;

    //== builder methods ==
    public LoginRequestDto email(String email) {
        this.email = email;
        return this;
    }

    public LoginRequestDto password(String password) {
        this.password = password;
        return this;
    }

}