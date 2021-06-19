package com.pu.georgidinov.pupracticumvoltwo.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;

    @Override
    public String toString() {
        return "RegistrationRequestDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    //== builder methods ==
    public RegistrationRequestDto firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public RegistrationRequestDto lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public RegistrationRequestDto email(String email) {
        this.email = email;
        return this;
    }

    public RegistrationRequestDto password(String password) {
        this.password = password;
        return this;
    }

}