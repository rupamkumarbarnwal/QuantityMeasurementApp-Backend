package com.app.mesurmentservice.service;


import com.app.mesurmentservice.QuantityInputDTO;
import com.app.mesurmentservice.QuantityRepository;
import com.app.mesurmentservice.ResponseDTO;
import com.app.mesurmentservice.quanity.Quantity;
import com.app.mesurmentservice.units.LengthUnit;
import com.app.mesurmentservice.units.TemperatureUnit;
import com.app.mesurmentservice.units.VolumeUnit;
import com.app.mesurmentservice.units.WeightUnit;
import com.app.mesurmentservice.QuantityMeasurementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

@Service
public class QuantityMeasurementService implements IQuantityMeasurementService {

    @Autowired
    private QuantityRepository repository;

    // Helper method to get current user ID from authentication context
    private String getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                String user = authentication.getName();

                System.out.println("🔥 Logged-in user: " + user);  // ✅ ADD THIS

                return user;
            }

        } catch (Exception e) {
            System.err.println("❌ Error getting user: " + e.getMessage());
        }

        return null;
    }

    // 🔥 Build Quantity dynamically
    private Quantity<?> buildQuantity(double value, String unit, String type) {

        switch (type.toLowerCase()) {

            case "length":
                return new Quantity<>(value, LengthUnit.valueOf(unit.toUpperCase()));

            case "weight":
                return new Quantity<>(value, WeightUnit.valueOf(unit.toUpperCase()));

            case "temperature":
                return new Quantity<>(value, TemperatureUnit.valueOf(unit.toUpperCase()));

            case "volume":
                return new Quantity<>(value, VolumeUnit.valueOf(unit.toUpperCase()));

            default:
                throw new RuntimeException("Invalid type: " + type);
        }
    }

    // 🔥 Compare
    @Override
    public ResponseDTO compareQuantities(QuantityInputDTO dto) {

        String type = dto.getThisQuantityDTO().getType();

        Quantity<?> q1 = buildQuantity(
                dto.getThisQuantityDTO().getValue(),
                dto.getThisQuantityDTO().getUnit(),
                type
        );

        Quantity<?> q2 = buildQuantity(
                dto.getThatQuantityDTO().getValue(),
                dto.getThatQuantityDTO().getUnit(),
                type
        );

        boolean result = q1.equals(q2);

        ResponseDTO response = new ResponseDTO();
        response.setResultString(String.valueOf(result));
        response.setResultValue(result ? 1.0 : 0.0);

        saveToDB("COMPARE", dto, String.valueOf(result));

        return response;
    }

    // 🔥 Add
    @Override
    public ResponseDTO addQuantities(QuantityInputDTO dto) {

        String type = dto.getThisQuantityDTO().getType();

        if (!type.equalsIgnoreCase(dto.getThatQuantityDTO().getType())) {
            throw new RuntimeException("Different types not allowed");
        }

        ResponseDTO response = new ResponseDTO();

        switch (type.toLowerCase()) {

            case "length": {
                Quantity<LengthUnit> q1 = (Quantity<LengthUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<LengthUnit> q2 = (Quantity<LengthUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                response.setResultValue(q1.add(q2).getValue());
                break;
            }

            case "weight": {
                Quantity<WeightUnit> q1 = (Quantity<WeightUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<WeightUnit> q2 = (Quantity<WeightUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                response.setResultValue(q1.add(q2).getValue());
                break;
            }

            case "volume": {
                Quantity<VolumeUnit> q1 = (Quantity<VolumeUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<VolumeUnit> q2 = (Quantity<VolumeUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                response.setResultValue(q1.add(q2).getValue());
                break;
            }

            case "temperature":
                throw new RuntimeException("Addition not supported for temperature");

            default:
                throw new RuntimeException("Invalid type");
        }

        saveToDB("ADD", dto, String.valueOf(response.getResultValue()));

        return response;
    }

    // 🔥 Divide
    @Override
    public ResponseDTO divideQuantities(QuantityInputDTO dto) {

        String type = dto.getThisQuantityDTO().getType();

        if (!type.equalsIgnoreCase(dto.getThatQuantityDTO().getType())) {
            throw new RuntimeException("Different types not allowed");
        }

        double result;

        switch (type.toLowerCase()) {

            case "length": {
                Quantity<LengthUnit> q1 = (Quantity<LengthUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<LengthUnit> q2 = (Quantity<LengthUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                result = q1.divide(q2);
                break;
            }

            case "weight": {
                Quantity<WeightUnit> q1 = (Quantity<WeightUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<WeightUnit> q2 = (Quantity<WeightUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                result = q1.divide(q2);
                break;
            }

            case "volume": {
                Quantity<VolumeUnit> q1 = (Quantity<VolumeUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<VolumeUnit> q2 = (Quantity<VolumeUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                result = q1.divide(q2);
                break;
            }

            default:
                throw new RuntimeException("Invalid type");
        }

        ResponseDTO response = new ResponseDTO();
        response.setResultValue(result);

        saveToDB("DIVIDE", dto, String.valueOf(result));

        return response;
    }

    // 🔥 Convert
    @Override
    public ResponseDTO convertQuantities(QuantityInputDTO dto) {

        String type = dto.getThisQuantityDTO().getType();
        String targetUnit = dto.getThisQuantityDTO().getTargetUnit();

        if (targetUnit == null) {
            throw new RuntimeException("Target unit is required");
        }

        ResponseDTO response = new ResponseDTO();

        switch (type.toLowerCase()) {

            case "length": {
                Quantity<LengthUnit> q = (Quantity<LengthUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                LengthUnit target = LengthUnit.valueOf(targetUnit.toUpperCase());

                response.setResultValue(q.convertTo(target).getValue());
                break;
            }

            case "weight": {
                Quantity<WeightUnit> q = (Quantity<WeightUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                WeightUnit target = WeightUnit.valueOf(targetUnit.toUpperCase());

                response.setResultValue(q.convertTo(target).getValue());
                break;
            }

            case "temperature": {
                Quantity<TemperatureUnit> q = (Quantity<TemperatureUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                TemperatureUnit target = TemperatureUnit.valueOf(targetUnit.toUpperCase());

                response.setResultValue(q.convertTo(target).getValue());
                break;
            }

            case "volume": {
                Quantity<VolumeUnit> q = (Quantity<VolumeUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                VolumeUnit target = VolumeUnit.valueOf(targetUnit.toUpperCase());

                response.setResultValue(q.convertTo(target).getValue());
                break;
            }

            default:
                throw new RuntimeException("Invalid type");
        }

        saveToDB("CONVERT", dto, String.valueOf(response.getResultValue()));

        return response;
    }

    // 🔥 Subtract
    @Override
    public ResponseDTO subtractQuantities(QuantityInputDTO dto) {

        String type = dto.getThisQuantityDTO().getType();

        if (!type.equalsIgnoreCase(dto.getThatQuantityDTO().getType())) {
            throw new RuntimeException("Different types not allowed");
        }

        ResponseDTO response = new ResponseDTO();

        switch (type.toLowerCase()) {

            case "length": {
                Quantity<LengthUnit> q1 = (Quantity<LengthUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<LengthUnit> q2 = (Quantity<LengthUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                response.setResultValue(q1.subtract(q2).getValue());
                break;
            }

            case "weight": {
                Quantity<WeightUnit> q1 = (Quantity<WeightUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<WeightUnit> q2 = (Quantity<WeightUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                response.setResultValue(q1.subtract(q2).getValue());
                break;
            }

            case "volume": {
                Quantity<VolumeUnit> q1 = (Quantity<VolumeUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<VolumeUnit> q2 = (Quantity<VolumeUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                response.setResultValue(q1.subtract(q2).getValue());
                break;
            }

            case "temperature":
                throw new RuntimeException("Subtraction not supported for temperature");

            default:
                throw new RuntimeException("Invalid type");
        }

        saveToDB("SUBTRACT", dto, String.valueOf(response.getResultValue()));

        return response;
    }

    // 🔥 Multiply
    @Override
    public ResponseDTO multiplyQuantities(QuantityInputDTO dto) {

        String type = dto.getThisQuantityDTO().getType();

        if (!type.equalsIgnoreCase(dto.getThatQuantityDTO().getType())) {
            throw new RuntimeException("Different types not allowed");
        }

        double result;

        switch (type.toLowerCase()) {

            case "length": {
                Quantity<LengthUnit> q1 = (Quantity<LengthUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<LengthUnit> q2 = (Quantity<LengthUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                result = q1.multiply(q2);
                break;
            }

            case "weight": {
                Quantity<WeightUnit> q1 = (Quantity<WeightUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<WeightUnit> q2 = (Quantity<WeightUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                result = q1.multiply(q2);
                break;
            }

            case "volume": {
                Quantity<VolumeUnit> q1 = (Quantity<VolumeUnit>) buildQuantity(
                        dto.getThisQuantityDTO().getValue(),
                        dto.getThisQuantityDTO().getUnit(),
                        type
                );

                Quantity<VolumeUnit> q2 = (Quantity<VolumeUnit>) buildQuantity(
                        dto.getThatQuantityDTO().getValue(),
                        dto.getThatQuantityDTO().getUnit(),
                        type
                );

                result = q1.multiply(q2);
                break;
            }

            case "temperature":
                throw new RuntimeException("Multiplication not supported for temperature");

            default:
                throw new RuntimeException("Invalid type");
        }

        ResponseDTO response = new ResponseDTO();
        response.setResultValue(result);

        saveToDB("MULTIPLY", dto, String.valueOf(result));

        return response;
    }

    // 🔥 Common DB Save Method - NOW CAPTURES USERID
    private void saveToDB(String operation, QuantityInputDTO dto, String result) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setOperation(operation);

        // operand1 always present
        entity.setOperand1(
                dto.getThisQuantityDTO().getValue() + " " +
                        dto.getThisQuantityDTO().getUnit()
        );

        // 🔥 FIX: check null
        if (dto.getThatQuantityDTO() != null) {
            entity.setOperand2(
                    dto.getThatQuantityDTO().getValue() + " " +
                            dto.getThatQuantityDTO().getUnit()
            );
        } else {
            entity.setOperand2("N/A");
        }

        entity.setResult(result);

        // NEW: Capture userId for tracking
        String userId = getCurrentUserId();

        System.out.println("💾 Saving userId: " + userId); // ✅ ADD THIS

        entity.setUserId(userId);

        // Set timestamp
        entity.setTimestamp(System.currentTimeMillis());

        repository.save(entity);
    }

    // 🔥 Remaining methods (optional)
    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationIgnoreCase(operation);
    }

    @Override
    public java.util.List<ResponseDTO> getHistoryByOperation(String operation) {

        java.util.List<QuantityMeasurementEntity> list =
                repository.findByOperationIgnoreCase(operation);

        java.util.List<ResponseDTO> responseList = new ArrayList<>();

        for (QuantityMeasurementEntity e : list) {
            ResponseDTO dto = new ResponseDTO();

            dto.setResultString(
                    e.getOperand1() + " , " +
                            e.getOperand2() + " → " +
                            e.getResult()
            );

            responseList.add(dto);
        }

        return responseList;
    }

    @Override
    public java.util.List<ResponseDTO> getHistoryByType(String type) {

        java.util.List<QuantityMeasurementEntity> list = repository.findAll();

        java.util.List<ResponseDTO> responseList = new ArrayList<>();

        for (QuantityMeasurementEntity e : list) {

            // simple filter (based on unit keywords)
            if (e.getOperand1().toLowerCase().contains(type.toLowerCase())) {

                ResponseDTO dto = new ResponseDTO();

                dto.setResultString(
                        e.getOperand1() + " , " +
                                e.getOperand2() + " → " +
                                e.getResult()
                );

                responseList.add(dto);
            }
        }

        return responseList;
    }

    @Override
    public java.util.List<ResponseDTO> getErrorHistory() {

        java.util.List<QuantityMeasurementEntity> list = repository.findAll();

        java.util.List<ResponseDTO> responseList = new ArrayList<>();

        for (QuantityMeasurementEntity e : list) {

            if (e.getResult().toLowerCase().contains("error")) {

                ResponseDTO dto = new ResponseDTO();

                dto.setResultString(
                        e.getOperand1() + " , " +
                                e.getOperand2() + " → " +
                                e.getResult()
                );

                responseList.add(dto);
            }
        }

        return responseList;
    }


    // ========== NEW: USER-SPECIFIC METHODS ==========

    @Override
    public long getUserOperationCount(String userId, String operation) {
        return repository.countByOperationIgnoreCaseAndUserId(operation, userId);
    }

    @Override
    public java.util.List<ResponseDTO> getUserHistoryByOperation(String userId, String operation) {

        java.util.List<QuantityMeasurementEntity> list =
                repository.findByUserIdAndOperationIgnoreCaseOrderByTimestampDesc(userId, operation);

        java.util.List<ResponseDTO> responseList = new ArrayList<>();

        for (QuantityMeasurementEntity e : list) {
            ResponseDTO dto = new ResponseDTO();

            dto.setResultString(
                    e.getOperand1() + " , " +
                            e.getOperand2() + " → " +
                            e.getResult()
            );

            responseList.add(dto);
        }

        return responseList;
    }

    @Override
    public java.util.List<ResponseDTO> getUserHistoryByType(String userId, String type) {

        java.util.List<QuantityMeasurementEntity> list = repository.findByUserIdOrderByTimestampDesc(userId);

        java.util.List<ResponseDTO> responseList = new ArrayList<>();

        for (QuantityMeasurementEntity e : list) {

            // simple filter (based on unit keywords)
            if (e.getOperand1().toLowerCase().contains(type.toLowerCase())) {

                ResponseDTO dto = new ResponseDTO();

                dto.setResultString(
                        e.getOperand1() + " , " +
                                e.getOperand2() + " → " +
                                e.getResult()
                );

                responseList.add(dto);
            }
        }

        return responseList;
    }

    @Override
    public java.util.List<ResponseDTO> getUserHistory(String userId) {

        java.util.List<QuantityMeasurementEntity> list =
                repository.findByUserIdOrderByTimestampDesc(userId);

        java.util.List<ResponseDTO> responseList = new ArrayList<>();

        for (QuantityMeasurementEntity e : list) {
            ResponseDTO dto = new ResponseDTO();

            dto.setResultString(
                    e.getOperation() + " | " +
                            e.getOperand1() + " , " +
                            e.getOperand2() + " → " +
                            e.getResult()
            );

            responseList.add(dto);
        }

        return responseList;
    }

    @Override
    public java.util.List<ResponseDTO> getUserErrorHistory(String userId) {

        java.util.List<QuantityMeasurementEntity> list =
                repository.findByUserIdOrderByTimestampDesc(userId);

        java.util.List<ResponseDTO> responseList = new ArrayList<>();

        for (QuantityMeasurementEntity e : list) {

            if (e.getResult().toLowerCase().contains("error")) {

                ResponseDTO dto = new ResponseDTO();

                dto.setResultString(
                        e.getOperand1() + " , " +
                                e.getOperand2() + " → " +
                                e.getResult()
                );

                responseList.add(dto);
            }
        }

        return responseList;
    }
}
