package com.quantitymeasurement.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuantityInputDTO {

    private QuantityDTO thisQuantityDTO;
    private QuantityDTO thatQuantityDTO;

}