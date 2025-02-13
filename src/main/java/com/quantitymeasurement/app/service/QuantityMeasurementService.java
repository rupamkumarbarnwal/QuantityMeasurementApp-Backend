package com.quantitymeasurement.app.service;

import com.quantitymeasurement.app.core.Quantity;
import com.quantitymeasurement.app.dto.QuantityInputDTO;
import com.quantitymeasurement.app.dto.ResponseDTO;
import com.quantitymeasurement.app.entity.QuantityMeasurementEntity;
import com.quantitymeasurement.app.repository.IQuantityMeasurementRepository;
import com.quantitymeasurement.app.units.LengthUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuantityMeasurementService implements IQuantityMeasurementService {
    @Autowired
    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementService(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        this.repository.initializeDatabase();
    }

    private Quantity<LengthUnit> buildQuantity(double value, String unit) {
        return new Quantity<>(
                value,
                LengthUnit.valueOf(unit.toUpperCase())
        );
    }

    @Override
    public ResponseDTO compareQuantities(QuantityInputDTO dto) {

        Quantity<LengthUnit> q1 = buildQuantity(
                dto.getThisQuantityDTO().getValue(),
                dto.getThisQuantityDTO().getUnit()
        );

        Quantity<LengthUnit> q2 = buildQuantity(
                dto.getThatQuantityDTO().getValue(),
                dto.getThatQuantityDTO().getUnit()
        );

        boolean result = q1.equals(q2);

        ResponseDTO response = new ResponseDTO();
        response.setResultString(String.valueOf(result));
        response.setResultValue(result ? 1.0 : 0.0);

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation("COMPARE");
        entity.setOperand1(dto.getThisQuantityDTO().getValue() + " " + dto.getThisQuantityDTO().getUnit());
        entity.setOperand2(dto.getThatQuantityDTO().getValue() + " " + dto.getThatQuantityDTO().getUnit());
        entity.setResult(String.valueOf(result));

        repository.save(entity);

        return response;
    }

    @Override
    public ResponseDTO addQuantities(QuantityInputDTO dto) {

        Quantity<LengthUnit> q1 = buildQuantity(
                dto.getThisQuantityDTO().getValue(),
                dto.getThisQuantityDTO().getUnit()
        );

        Quantity<LengthUnit> q2 = buildQuantity(
                dto.getThatQuantityDTO().getValue(),
                dto.getThatQuantityDTO().getUnit()
        );

        Quantity<LengthUnit> resultQuantity = q1.add(q2);

        ResponseDTO response = new ResponseDTO();
        response.setResultValue(resultQuantity.getValue());

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation("ADD");
        entity.setOperand1(dto.getThisQuantityDTO().getValue() + " " + dto.getThisQuantityDTO().getUnit());
        entity.setOperand2(dto.getThatQuantityDTO().getValue() + " " + dto.getThatQuantityDTO().getUnit());
        entity.setResult(String.valueOf(resultQuantity.getValue()));

        repository.save(entity);

        return response;
    }

    @Override
    public ResponseDTO divideQuantities(QuantityInputDTO dto) {

        Quantity<LengthUnit> q1 = buildQuantity(
                dto.getThisQuantityDTO().getValue(),
                dto.getThisQuantityDTO().getUnit()
        );

        Quantity<LengthUnit> q2 = buildQuantity(
                dto.getThatQuantityDTO().getValue(),
                dto.getThatQuantityDTO().getUnit()
        );

        double result = q1.divide(q2);

        ResponseDTO response = new ResponseDTO();
        response.setResultValue(result);

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation("DIVIDE");
        entity.setOperand1(dto.getThisQuantityDTO().getValue() + " " + dto.getThisQuantityDTO().getUnit());
        entity.setOperand2(dto.getThatQuantityDTO().getValue() + " " + dto.getThatQuantityDTO().getUnit());
        entity.setResult(String.valueOf(result));

        repository.save(entity);

        return response;
    }

    @Override
    public ResponseDTO convertQuantities(QuantityInputDTO dto) {

        Quantity<LengthUnit> q1 = buildQuantity(
                dto.getThisQuantityDTO().getValue(),
                dto.getThisQuantityDTO().getUnit()
        );

        // convert to FEET (base unit)
        Quantity<LengthUnit> converted = q1.convertTo(LengthUnit.FEET);

        ResponseDTO response = new ResponseDTO();
        response.setResultValue(converted.getValue());

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation("CONVERT");
        entity.setOperand1(dto.getThisQuantityDTO().getValue() + " " + dto.getThisQuantityDTO().getUnit());
        entity.setOperand2("FEET");
        entity.setResult(String.valueOf(converted.getValue()));

        repository.save(entity);

        return response;
    }

    // You can keep your previous implementations for history methods
    @Override
    public long getOperationCount(String operation) {
        return 0;
    }

    @Override
    public java.util.List<ResponseDTO> getHistoryByOperation(String operation) {
        return new java.util.ArrayList<>();
    }

    @Override
    public java.util.List<ResponseDTO> getHistoryByType(String type) {
        return new java.util.ArrayList<>();
    }

    @Override
    public java.util.List<ResponseDTO> getErrorHistory() {
        return new java.util.ArrayList<>();
    }
}