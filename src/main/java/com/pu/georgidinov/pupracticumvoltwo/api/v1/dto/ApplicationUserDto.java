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
public class ApplicationUserDto {

    @JsonProperty("user_id")
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;


    @Override
    public String toString() {
        return "ApplicationUserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    //== builder methods ==
    public ApplicationUserDto id(Long id) {
        this.id = id;
        return this;
    }

    public ApplicationUserDto firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ApplicationUserDto lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

}