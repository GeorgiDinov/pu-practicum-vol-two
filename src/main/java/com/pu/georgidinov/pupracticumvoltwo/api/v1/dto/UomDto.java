package com.pu.georgidinov.pupracticumvoltwo.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UomDto {

    private Long id;
    private String description;

    @Override
    public String toString() {
        return "UomDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    //== builder methods ==
    public UomDto id(long id) {
        this.id = id;
        return this;
    }

    public UomDto description(String description) {
        this.description = description;
        return this;
    }
}