package com.app.mesurmentservice;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuantityInputDTO {

    private QuantityDTO thisQuantityDTO;
    private QuantityDTO thatQuantityDTO;

}