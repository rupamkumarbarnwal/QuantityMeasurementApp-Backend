package com.quantitymeasurement.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuantityDTO {
    private String targetUnit;

    private double value;
    private String unit;
    private String type;

}