package com.app.quantityMeasurement.service;

import com.app.quantityMeasurement.entity.QuantityDTO;

public interface IQuantityMeasurementService {

    boolean compare(QuantityDTO thisQuantityDTO,
                    QuantityDTO thatQuantityDTO);

    QuantityDTO convert(QuantityDTO thisQuantityDTO,
                        QuantityDTO thatQuantityDTO);

    QuantityDTO add(QuantityDTO thisQuantityDTO,
                    QuantityDTO thatQuantityDTO);

    QuantityDTO add(QuantityDTO thisQuantityDTO,
                    QuantityDTO thatQuantityDTO,
                    QuantityDTO targetUnitDTO);

    QuantityDTO subtract(QuantityDTO thisQuantityDTO,
                         QuantityDTO thatQuantityDTO);

    QuantityDTO subtract(QuantityDTO thisQuantityDTO,
                         QuantityDTO thatQuantityDTO,
                         QuantityDTO targetUnitDTO);

    double divide(QuantityDTO thisQuantityDTO,
                  QuantityDTO thatQuantityDTO);

    public static void main(String[] args) {
        System.out.println("IQuantityMeasurementService");
    }
}