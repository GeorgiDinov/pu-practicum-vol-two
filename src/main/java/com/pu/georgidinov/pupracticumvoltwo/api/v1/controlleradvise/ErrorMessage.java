package com.pu.georgidinov.pupracticumvoltwo.api.v1.controlleradvise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorMessage {

    @JsonProperty("error_message")
    private String message;

}