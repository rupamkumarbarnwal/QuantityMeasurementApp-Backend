package com.quantitymeasurement.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "quantity_measurement")
public class QuantityMeasurementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operation;
    private String operand1;
    private String operand2;
    private String result;

    // NEW: Add userId to track which user performed this operation
    private String userId;

    // Timestamp for tracking when the operation was performed
    @Column(nullable = true)
    private Long timestamp = System.currentTimeMillis();
}
