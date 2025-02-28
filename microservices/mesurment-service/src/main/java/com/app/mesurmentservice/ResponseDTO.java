package com.app.mesurmentservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private String resultString;
    private Double resultValue;


}