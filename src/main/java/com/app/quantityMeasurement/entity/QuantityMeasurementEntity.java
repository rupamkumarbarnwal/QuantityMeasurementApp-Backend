package com.app.quantityMeasurement.entity;

import com.app.quantityMeasurement.unit.IMeasurable;

import java.io.Serializable;
import java.time.LocalDateTime;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public double        thisValue;
    public String        thisUnit;
    public String        thisMeasurementType;

    public double        thatValue;
    public String        thatUnit;
    public String        thatMeasurementType;

    public String        operation;

    public double        resultValue;
    public String        resultUnit;
    public String        resultMeasurementType;
    public String        resultString;

    public boolean       isError;
    public String        errorMessage;

    public LocalDateTime createdAt;

    // ── No-arg constructor (required by mapResultSetToEntity) ────────────────
    public QuantityMeasurementEntity() {
        this.createdAt = LocalDateTime.now();
    }

    // ── Private base constructor ──────────────────────────────────────────────
    private QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisModel,
            QuantityModel<IMeasurable> thatModel,
            String operation) {
        this.thisValue           =
                thisModel.getValue();
        this.thisUnit            =
                thisModel.getUnit().getUnitName();
        this.thisMeasurementType =
                thisModel.getUnit().getMeasurementType();

        this.thatValue           =
                thatModel.getValue();
        this.thatUnit            =
                thatModel.getUnit().getUnitName();
        this.thatMeasurementType =
                thatModel.getUnit().getMeasurementType();

        this.operation = operation;
        this.isError   = false;
        this.createdAt = LocalDateTime.now();
    }

    // ── Constructor 1 — String result (compare/convert) ──────────────────────
    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisModel,
            QuantityModel<IMeasurable> thatModel,
            String operation,
            String resultString) {
        this(thisModel, thatModel, operation);
        this.resultString = resultString;
    }

    // ── Constructor 2 — QuantityModel result (add/subtract) ──────────────────
    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisModel,
            QuantityModel<IMeasurable> thatModel,
            String operation,
            QuantityModel<IMeasurable> resultModel) {
        this(thisModel, thatModel, operation);
        this.resultValue           =
                resultModel.getValue();
        this.resultUnit            =
                resultModel.getUnit().getUnitName();
        this.resultMeasurementType =
                resultModel.getUnit().getMeasurementType();
    }

    // ── Constructor 3 — double result (divide) ────────────────────────────────
    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisModel,
            QuantityModel<IMeasurable> thatModel,
            String operation,
            double resultValue) {
        this(thisModel, thatModel, operation);
        this.resultValue = resultValue;
    }

    // ── Constructor 4 — error case ────────────────────────────────────────────
    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisModel,
            QuantityModel<IMeasurable> thatModel,
            String operation,
            String errorMessage,
            boolean isError) {
        this(thisModel, thatModel, operation);
        this.errorMessage = errorMessage;
        this.isError      = isError;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public double getThisValue() {
        return thisValue;
    }

    public String getThisUnit() {
        return thisUnit;
    }

    public String getThisMeasurementType() {
        return thisMeasurementType;
    }

    public double getThatValue() {
        return thatValue;
    }

    public String getThatUnit() {
        return thatUnit;
    }

    public String getThatMeasurementType() {
        return thatMeasurementType;
    }

    public String getOperation() {
        return operation;
    }

    public double getResultValue() {
        return resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public String getResultMeasurementType() {
        return resultMeasurementType;
    }

    public String getResultString() {
        return resultString;
    }

    public boolean isError() {
        return isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ── toString ──────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        if (isError) {
            return String.format(
                    "QuantityMeasurementEntity{" +
                    "operation='%s', " +
                    "this='%s %s', " +
                    "that='%s %s', " +
                    "isError=true, " +
                    "errorMessage='%s', " +
                    "createdAt='%s'}",
                    operation,
                    thisValue,   thisUnit,
                    thatValue,   thatUnit,
                    errorMessage,
                    createdAt);
        }
        if (resultString != null) {
            return String.format(
                    "QuantityMeasurementEntity{" +
                    "operation='%s', " +
                    "this='%s %s', " +
                    "that='%s %s', " +
                    "result='%s', " +
                    "createdAt='%s'}",
                    operation,
                    thisValue, thisUnit,
                    thatValue, thatUnit,
                    resultString,
                    createdAt);
        }
        return String.format(
                "QuantityMeasurementEntity{" +
                "operation='%s', " +
                "this='%s %s', " +
                "that='%s %s', " +
                "result='%s %s', " +
                "createdAt='%s'}",
                operation,
                thisValue,   thisUnit,
                thatValue,   thatUnit,
                resultValue, resultUnit,
                createdAt);
    }

    public static void main(String[] args) {
        System.out.println("QuantityMeasurementEntity");
    }
}