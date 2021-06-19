package com.pu.georgidinov.pupracticumvoltwo.api.v1.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UomDtoList {

    @JsonProperty("units")
    private List<UomDto> uomDtoList = new ArrayList<>();

    public UomDtoList() {
    }

    public UomDtoList(List<UomDto> uomDtoList) {
        this.setUomDtoList(uomDtoList);
    }

    public List<UomDto> getUomDtoList() {
        return this.uomDtoList;
    }

    public void setUomDtoList(List<UomDto> uomDtoList) {
        if (uomDtoList != null) {
            this.uomDtoList = uomDtoList;
        }
    }

}