package com.app.quantityMeasurement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quantity_measurement_entity",
        indexes = {
                @Index(name = "idx_operation",
                        columnList = "operation"),
                @Index(name = "idx_this_measurement_type",
                        columnList = "thisMeasurementType"),
                @Index(name = "idx_that_measurement_type",
                        columnList = "thatMeasurementType"),
                @Index(name = "idx_created_at",
                        columnList = "createdAt"),
                @Index(name = "idx_is_error",
                        columnList = "isError")
        })
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double thisValue;

    @Column(nullable = false)
    private String thisUnit;

    @Column(nullable = false)
    private String thisMeasurementType;

    @Column(nullable = false)
    private double thatValue;

    @Column(nullable = false)
    private String thatUnit;

    @Column(nullable = false)
    private String thatMeasurementType;

    @Column(nullable = false)
    private String operation;

    private String resultString;
    private double resultValue;
    private String resultUnit;
    private String resultMeasurementType;

    @Column(nullable = false)
    private boolean isError;

    private String errorMessage;

    @Column(nullable = false,
            updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
