package quantityMeasurement.controller;

import quantityMeasurement.QuantityMeasurementException;
import quantityMeasurement.model.QuantityDTO;
import quantityMeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private IQuantityMeasurementService quantityMeasurementService;

    public QuantityMeasurementController(
            IQuantityMeasurementService quantityMeasurementService) {
        this.quantityMeasurementService = quantityMeasurementService;
    }

    public boolean performComparison(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            boolean result = quantityMeasurementService
                    .compare(thisQuantityDTO, thatQuantityDTO);
            System.out.println(String.format(
                    "%s %s == %s %s : %s",
                    thisQuantityDTO.getValue(), thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(), thatQuantityDTO.getUnit(),
                    result ? "Equal" : "Not Equal"));
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public QuantityDTO performConversion(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            QuantityDTO result = quantityMeasurementService
                    .convert(thisQuantityDTO, thatQuantityDTO);
            System.out.println(String.format(
                    "%s %s => %s %s",
                    thisQuantityDTO.getValue(), thisQuantityDTO.getUnit(),
                    result.getValue(),          result.getUnit()));
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public QuantityDTO performAddition(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            QuantityDTO result = quantityMeasurementService
                    .add(thisQuantityDTO, thatQuantityDTO);
            System.out.println(String.format(
                    "%s %s + %s %s = %s %s",
                    thisQuantityDTO.getValue(), thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(), thatQuantityDTO.getUnit(),
                    result.getValue(),          result.getUnit()));
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public QuantityDTO performAddition(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO) {
        try {
            QuantityDTO result = quantityMeasurementService
                    .add(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
            System.out.println(String.format(
                    "%s %s + %s %s = %s %s",
                    thisQuantityDTO.getValue(), thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(), thatQuantityDTO.getUnit(),
                    result.getValue(),          result.getUnit()));
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public QuantityDTO performSubtraction(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            QuantityDTO result = quantityMeasurementService
                    .subtract(thisQuantityDTO, thatQuantityDTO);
            System.out.println(String.format(
                    "%s %s - %s %s = %s %s",
                    thisQuantityDTO.getValue(), thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(), thatQuantityDTO.getUnit(),
                    result.getValue(),          result.getUnit()));
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public QuantityDTO performSubtraction(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO) {
        try {
            QuantityDTO result = quantityMeasurementService
                    .subtract(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
            System.out.println(String.format(
                    "%s %s - %s %s = %s %s",
                    thisQuantityDTO.getValue(), thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(), thatQuantityDTO.getUnit(),
                    result.getValue(),          result.getUnit()));
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public double performDivision(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            double result = quantityMeasurementService
                    .divide(thisQuantityDTO, thatQuantityDTO);
            System.out.println(String.format(
                    "%s %s / %s %s = %s",
                    thisQuantityDTO.getValue(), thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(), thatQuantityDTO.getUnit(),
                    result));
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR: " + e.getMessage());
            return 0.0;
        } catch (Exception e) {
            System.err.println("UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
            return 0.0;
        }
    }

    public static void main(String[] args) {
        System.out.println("QuantityMeasurementController");
    }
}