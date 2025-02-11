package quantityMeasurement.application;

import quantityMeasurement.QuantityMeasurementCacheRepository;
import quantityMeasurement.QuantityMeasurementException;
import quantityMeasurement.controller.QuantityMeasurementController;
import quantityMeasurement.model.QuantityDTO;
import quantityMeasurement.model.QuantityDTO.LengthUnit;
import quantityMeasurement.model.QuantityDTO.TemperatureUnit;
import quantityMeasurement.model.QuantityDTO.VolumeUnit;
import quantityMeasurement.model.QuantityDTO.WeightUnit;
import quantityMeasurement.repository.IQuantityMeasurementRepository;
import quantityMeasurement.service.IQuantityMeasurementService;
import quantityMeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    private static QuantityMeasurementApp instance;

    public QuantityMeasurementController controller;
    public IQuantityMeasurementRepository repository;

    private QuantityMeasurementApp() {
        this.repository = QuantityMeasurementCacheRepository.getInstance();
        IQuantityMeasurementService service =
                createQuantityMeasurementService(this.repository);
        this.controller = createQuantityMeasurementController(service);
    }

    public static QuantityMeasurementApp getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementApp();
        }
        return instance;
    }

    private static IQuantityMeasurementService createQuantityMeasurementService(
            IQuantityMeasurementRepository repository) {
        return new QuantityMeasurementServiceImpl(repository);
    }

    private static QuantityMeasurementController createQuantityMeasurementController(
            IQuantityMeasurementService service) {
        return new QuantityMeasurementController(service);
    }

    public static void main(String[] args) {
        QuantityMeasurementApp app = QuantityMeasurementApp.getInstance();

        System.out.println("\n=== Length Comparison ===");
        QuantityDTO l1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO l2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        app.controller.performComparison(l1, l2);

        System.out.println("\n=== Length Conversion ===");
        QuantityDTO l3 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO l4 = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);
        app.controller.performConversion(l3, l4);

        System.out.println("\n=== Length Addition ===");
        QuantityDTO l5 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO l6 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        app.controller.performAddition(l5, l6);

        System.out.println("\n=== Length Subtraction ===");
        QuantityDTO l7 = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO l8 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        app.controller.performSubtraction(l7, l8);

        System.out.println("\n=== Length Division ===");
        QuantityDTO l9  = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO l10 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        app.controller.performDivision(l9, l10);

        System.out.println("\n=== Weight Comparison ===");
        QuantityDTO w1 = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO w2 = new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM);
        app.controller.performComparison(w1, w2);

        System.out.println("\n=== Weight Conversion ===");
        QuantityDTO w3 = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO w4 = new QuantityDTO(0.0, QuantityDTO.WeightUnit.GRAM);
        app.controller.performConversion(w3, w4);

        System.out.println("\n=== Weight Addition ===");
        QuantityDTO w5 = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO w6 = new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM);
        app.controller.performAddition(w5, w6);

        System.out.println("\n=== Volume Comparison ===");
        QuantityDTO v1 = new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE);
        QuantityDTO v2 = new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE);
        app.controller.performComparison(v1, v2);

        System.out.println("\n=== Volume Conversion ===");
        QuantityDTO v3 = new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE);
        QuantityDTO v4 = new QuantityDTO(0.0, QuantityDTO.VolumeUnit.MILLILITRE);
        app.controller.performConversion(v3, v4);

        System.out.println("\n=== Volume Addition ===");
        QuantityDTO v5 = new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE);
        QuantityDTO v6 = new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE);
        app.controller.performAddition(v5, v6);

        System.out.println("\n=== Temperature Comparison ===");
        QuantityDTO t1 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO t2 = new QuantityDTO(212.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        app.controller.performComparison(t1, t2);

        System.out.println("\n=== Temperature Conversion ===");
        QuantityDTO t3 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO t4 = new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        app.controller.performConversion(t3, t4);

        System.out.println("\n=== Temperature Addition Attempt ===");
        try {
            QuantityDTO t5 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
            QuantityDTO t6 = new QuantityDTO(50.0, QuantityDTO.TemperatureUnit.CELSIUS);
            app.controller.performAddition(t5, t6);
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n=== Cross Category Prevention ===");
        try {
            QuantityDTO c1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
            QuantityDTO c2 = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
            app.controller.performComparison(c1, c2);
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n=== Repository Contents ===");
        System.out.println("Total stored operations: " +
                app.repository.getAllMeasurements().size());
    }
}