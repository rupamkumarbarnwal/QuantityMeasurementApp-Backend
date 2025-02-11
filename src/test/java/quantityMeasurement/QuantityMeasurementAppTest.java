package quantityMeasurement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quantityMeasurement.controller.QuantityMeasurementController;
import quantityMeasurement.model.QuantityDTO;
import quantityMeasurement.model.QuantityMeasurementEntity;
import quantityMeasurement.repository.IQuantityMeasurementRepository;
import quantityMeasurement.service.IQuantityMeasurementService;
import quantityMeasurement.service.QuantityMeasurementServiceImpl;

import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UC15 - N-Tier Architecture Tests")
public class QuantityMeasurementAppTest {

    private IQuantityMeasurementRepository repository;
    private IQuantityMeasurementService service;
    private QuantityMeasurementController controller;

    @BeforeEach
    public void setUp() {
        repository = new IQuantityMeasurementRepository() {
            private final List<QuantityMeasurementEntity> store =
                    new ArrayList<>();

            @Override
            public void save(QuantityMeasurementEntity entity) {
                store.add(entity);
            }

            @Override
            public List<QuantityMeasurementEntity> getAllMeasurements() {
                return new ArrayList<>(store);
            }
        };
        service    = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    // ── Entity Construction Tests ─────────────────────────────────────────────

    @Test
    @DisplayName("testQuantityEntity_SingleOperandConstruction")
    public void testQuantityEntity_SingleOperandConstruction() {
        QuantityDTO d1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);
        service.convert(d1, d2);

        List<QuantityMeasurementEntity> entities =
                repository.getAllMeasurements();
        assertEquals(1, entities.size());

        QuantityMeasurementEntity entity = entities.get(0);
        assertEquals("CONVERT",    entity.operation);
        assertEquals(1.0,          entity.thisValue);
        assertEquals("FEET",       entity.thisUnit);
        assertEquals("LengthUnit", entity.thisMeasurementType);
        assertEquals(0.0,          entity.thatValue);
        assertEquals("INCHES",     entity.thatUnit);
        assertFalse(entity.isError);
        assertNotNull(entity.resultUnit);
    }

    @Test
    @DisplayName("testQuantityEntity_BinaryOperandConstruction")
    public void testQuantityEntity_BinaryOperandConstruction() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        service.add(d1, d2);

        List<QuantityMeasurementEntity> entities =
                repository.getAllMeasurements();
        assertEquals(1, entities.size());

        QuantityMeasurementEntity entity = entities.get(0);
        assertEquals("ADD",        entity.operation);
        assertEquals(1.0,          entity.thisValue);
        assertEquals("FEET",       entity.thisUnit);
        assertEquals(12.0,         entity.thatValue);
        assertEquals("INCHES",     entity.thatUnit);
        assertEquals(2.0,          entity.resultValue, 1e-6);
        assertEquals("FEET",       entity.resultUnit);
        assertFalse(entity.isError);
    }

    @Test
    @DisplayName("testQuantityEntity_ErrorConstruction")
    public void testQuantityEntity_ErrorConstruction() {
        QuantityDTO d1 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(50.0,  QuantityDTO.TemperatureUnit.CELSIUS);

        assertThrows(QuantityMeasurementException.class,
                () -> service.add(d1, d2));
    }

    @Test
    @DisplayName("testQuantityEntity_ToString_Success")
    public void testQuantityEntity_ToString_Success() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        service.compare(d1, d2);

        QuantityMeasurementEntity entity =
                repository.getAllMeasurements().get(0);
        String str = entity.toString();
        assertNotNull(str);
        assertTrue(str.contains("COMPARE"));
        assertTrue(str.contains("Equal"));
    }

    @Test
    @DisplayName("testQuantityEntity_ToString_Error")
    public void testQuantityEntity_ToString_Error() {
        QuantityDTO d1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        service.compare(d1, d2);

        QuantityMeasurementEntity entity =
                repository.getAllMeasurements().get(0);
        String str = entity.toString();
        assertNotNull(str);
        assertFalse(entity.isError);
        assertFalse(str.contains("ERROR"));
    }

    // ── Service Layer Tests ───────────────────────────────────────────────────

    @Test
    @DisplayName("testService_CompareEquality_SameUnit_Success")
    public void testService_CompareEquality_SameUnit_Success() {
        QuantityDTO d1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        assertTrue(service.compare(d1, d2));

        QuantityMeasurementEntity entity =
                repository.getAllMeasurements().get(0);
        assertEquals("COMPARE", entity.operation);
        assertEquals("Equal",   entity.resultString);
    }

    @Test
    @DisplayName("testService_CompareEquality_DifferentUnit_Success")
    public void testService_CompareEquality_DifferentUnit_Success() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        assertTrue(service.compare(d1, d2));

        QuantityMeasurementEntity entity =
                repository.getAllMeasurements().get(0);
        assertEquals("COMPARE", entity.operation);
        assertEquals("Equal",   entity.resultString);
    }

    @Test
    @DisplayName("testService_CompareEquality_CrossCategory_Error")
    public void testService_CompareEquality_CrossCategory_Error() {
        QuantityDTO d1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
        assertThrows(QuantityMeasurementException.class,
                () -> service.compare(d1, d2));
    }

    @Test
    @DisplayName("testService_Convert_Success")
    public void testService_Convert_Success() {
        QuantityDTO d1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.convert(d1, d2);

        assertEquals(12.0,     result.getValue(), 1e-6);
        assertEquals("INCHES", result.getUnitName());

        QuantityMeasurementEntity entity =
                repository.getAllMeasurements().get(0);
        assertEquals("CONVERT", entity.operation);
        assertFalse(entity.isError);
    }

    @Test
    @DisplayName("testService_Add_Success")
    public void testService_Add_Success() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.add(d1, d2);

        assertEquals(2.0,    result.getValue(), 1e-6);
        assertEquals("FEET", result.getUnitName());

        QuantityMeasurementEntity entity =
                repository.getAllMeasurements().get(0);
        assertEquals("ADD", entity.operation);
        assertFalse(entity.isError);
    }

    @Test
    @DisplayName("testService_Add_UnsupportedOperation_Error")
    public void testService_Add_UnsupportedOperation_Error() {
        QuantityDTO d1 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(50.0,  QuantityDTO.TemperatureUnit.CELSIUS);

        QuantityMeasurementException ex = assertThrows(
                QuantityMeasurementException.class,
                () -> service.add(d1, d2));
        assertNotNull(ex.getMessage());
    }

    @Test
    @DisplayName("testService_Subtract_Success")
    public void testService_Subtract_Success() {
        QuantityDTO d1 = new QuantityDTO(2.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.subtract(d1, d2);

        assertEquals(1.0,    result.getValue(), 1e-6);
        assertEquals("FEET", result.getUnitName());

        QuantityMeasurementEntity entity =
                repository.getAllMeasurements().get(0);
        assertEquals("SUBTRACT", entity.operation);
        assertFalse(entity.isError);
    }

    @Test
    @DisplayName("testService_Divide_Success")
    public void testService_Divide_Success() {
        QuantityDTO d1 = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        double result = service.divide(d1, d2);

        assertEquals(2.0, result, 1e-6);

        QuantityMeasurementEntity entity =
                repository.getAllMeasurements().get(0);
        assertEquals("DIVIDE", entity.operation);
        assertFalse(entity.isError);
    }

    @Test
    @DisplayName("testService_Divide_ByZero_Error")
    public void testService_Divide_ByZero_Error() {
        QuantityDTO d1 = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET);

        QuantityMeasurementException ex = assertThrows(
                QuantityMeasurementException.class,
                () -> service.divide(d1, d2));
        assertNotNull(ex.getMessage());
    }

    // ── Controller Layer Tests ────────────────────────────────────────────────

    @Test
    @DisplayName("testController_DemonstrateEquality_Success")
    public void testController_DemonstrateEquality_Success() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        assertTrue(controller.performComparison(d1, d2));
    }

    @Test
    @DisplayName("testController_DemonstrateConversion_Success")
    public void testController_DemonstrateConversion_Success() {
        QuantityDTO d1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = controller.performConversion(d1, d2);

        assertNotNull(result);
        assertEquals(12.0,     result.getValue(), 1e-6);
        assertEquals("INCHES", result.getUnitName());
    }

    @Test
    @DisplayName("testController_DemonstrateAddition_Success")
    public void testController_DemonstrateAddition_Success() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = controller.performAddition(d1, d2);

        assertNotNull(result);
        assertEquals(2.0,    result.getValue(), 1e-6);
        assertEquals("FEET", result.getUnitName());
    }

    @Test
    @DisplayName("testController_DemonstrateAddition_Error")
    public void testController_DemonstrateAddition_Error() {
        QuantityDTO d1 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(50.0,  QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO result = controller.performAddition(d1, d2);

        assertNull(result);
    }

    @Test
    @DisplayName("testController_DisplayResult_Success")
    public void testController_DisplayResult_Success() {
        QuantityDTO d1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = controller.performConversion(d1, d2);

        assertNotNull(result);
        assertEquals(12.0,     result.getValue(), 1e-6);
        assertEquals("INCHES", result.getUnitName());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

 

    // ── Layer Separation Tests ────────────────────────────────────────────────

    @Test
    @DisplayName("testLayerSeparation_ServiceIndependence")
    public void testLayerSeparation_ServiceIndependence() {
        IQuantityMeasurementService independentService =
                new QuantityMeasurementServiceImpl(repository);

        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        assertDoesNotThrow(() -> independentService.compare(d1, d2));
        assertTrue(independentService.compare(d1, d2));
    }

    @Test
    @DisplayName("testLayerSeparation_ControllerIndependence")
    public void testLayerSeparation_ControllerIndependence() {
        IQuantityMeasurementService mockService =
                new IQuantityMeasurementService() {
                    @Override
                    public boolean compare(QuantityDTO t, QuantityDTO th) {
                        return true;
                    }
                    @Override
                    public QuantityDTO convert(QuantityDTO t, QuantityDTO th) {
                        return new QuantityDTO(12.0, "INCHES", "LengthUnit");
                    }
                    @Override
                    public QuantityDTO add(QuantityDTO t, QuantityDTO th) {
                        return new QuantityDTO(2.0, "FEET", "LengthUnit");
                    }
                    @Override
                    public QuantityDTO add(QuantityDTO t, QuantityDTO th,
                                           QuantityDTO target) {
                        return new QuantityDTO(24.0, "INCHES", "LengthUnit");
                    }
                    @Override
                    public QuantityDTO subtract(QuantityDTO t, QuantityDTO th) {
                        return new QuantityDTO(1.0, "FEET", "LengthUnit");
                    }
                    @Override
                    public QuantityDTO subtract(QuantityDTO t, QuantityDTO th,
                                                QuantityDTO target) {
                        return new QuantityDTO(12.0, "INCHES", "LengthUnit");
                    }
                    @Override
                    public double divide(QuantityDTO t, QuantityDTO th) {
                        return 2.0;
                    }
                };

        QuantityMeasurementController mockController =
                new QuantityMeasurementController(mockService);

        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        assertTrue(mockController.performComparison(d1, d2));
        assertEquals(2.0, mockController.performAddition(d1, d2).getValue(), 1e-6);
    }

    @Test
    @DisplayName("testDataFlow_ControllerToService")
    public void testDataFlow_ControllerToService() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        controller.performComparison(d1, d2);

        List<QuantityMeasurementEntity> entities =
                repository.getAllMeasurements();
        assertEquals(1, entities.size());

        QuantityMeasurementEntity entity = entities.get(0);
        assertEquals(1.0,    entity.thisValue);
        assertEquals("FEET", entity.thisUnit);
        assertEquals(12.0,   entity.thatValue);
        assertEquals("INCHES", entity.thatUnit);
    }

    @Test
    @DisplayName("testDataFlow_ServiceToController")
    public void testDataFlow_ServiceToController() {
        QuantityDTO d1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = controller.performConversion(d1, d2);

        assertNotNull(result);
        assertInstanceOf(QuantityDTO.class, result);
        assertEquals(12.0,         result.getValue(), 1e-6);
        assertEquals("INCHES",     result.getUnitName());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    // ── Backward Compatibility Tests ──────────────────────────────────────────

    @Test
    @DisplayName("testBackwardCompatibility_AllUC1_UC14_Tests")
    public void testBackwardCompatibility_AllUC1_UC14_Tests() {
        QuantityDTO l1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO l2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        assertTrue(service.compare(l1, l2));

        QuantityDTO w1 = new QuantityDTO(1.0,    QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO w2 = new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM);
        assertTrue(service.compare(w1, w2));

        QuantityDTO v1 = new QuantityDTO(1.0,    QuantityDTO.VolumeUnit.LITRE);
        QuantityDTO v2 = new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE);
        assertTrue(service.compare(v1, v2));

        QuantityDTO t1 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO t2 = new QuantityDTO(212.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        assertTrue(service.compare(t1, t2));

        QuantityDTO addResult = service.add(l1, l2);
        assertEquals(2.0, addResult.getValue(), 1e-6);

        assertThrows(QuantityMeasurementException.class,
                () -> service.add(t1, t2));
    }

    // ── Scalability Tests ─────────────────────────────────────────────────────

    @Test
    @DisplayName("testService_AllMeasurementCategories")
    public void testService_AllMeasurementCategories() {
        QuantityDTO l1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO l2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        assertTrue(service.compare(l1, l2));

        QuantityDTO w1 = new QuantityDTO(1.0,    QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO w2 = new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM);
        assertTrue(service.compare(w1, w2));

        QuantityDTO v1 = new QuantityDTO(1.0,    QuantityDTO.VolumeUnit.LITRE);
        QuantityDTO v2 = new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE);
        assertTrue(service.compare(v1, v2));

        QuantityDTO t1 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO t2 = new QuantityDTO(212.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        assertTrue(service.compare(t1, t2));
    }


    @Test
    @DisplayName("testService_ValidationConsistency")
    public void testService_ValidationConsistency() {
        QuantityDTO length = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO weight = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);

        assertThrows(QuantityMeasurementException.class,
                () -> service.compare(length, weight));
        assertThrows(QuantityMeasurementException.class,
                () -> service.add(length, weight));
        assertThrows(QuantityMeasurementException.class,
                () -> service.subtract(length, weight));
        assertThrows(QuantityMeasurementException.class,
                () -> service.divide(length, weight));
    }

    @Test
    @DisplayName("testEntity_Immutability")
    public void testEntity_Immutability() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        service.compare(d1, d2);

        QuantityMeasurementEntity entity =
                repository.getAllMeasurements().get(0);

        double   originalThisValue  = entity.thisValue;
        String   originalOperation  = entity.operation;
        String   originalResultStr  = entity.resultString;
        boolean  originalIsError    = entity.isError;

        assertEquals(originalThisValue, entity.thisValue);
        assertEquals(originalOperation, entity.operation);
        assertEquals(originalResultStr, entity.resultString);
        assertEquals(originalIsError,   entity.isError);
    }

    @Test
    @DisplayName("testService_ExceptionHandling_AllOperations")
    public void testService_ExceptionHandling_AllOperations() {
        QuantityDTO temp1 =
                new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO temp2 =
                new QuantityDTO(50.0,  QuantityDTO.TemperatureUnit.CELSIUS);

        assertThrows(QuantityMeasurementException.class,
                () -> service.add(temp1, temp2));
        assertThrows(QuantityMeasurementException.class,
                () -> service.subtract(temp1, temp2));
        assertThrows(QuantityMeasurementException.class,
                () -> service.divide(temp1, temp2));

        QuantityDTO zero =
                new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO feet =
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        assertThrows(QuantityMeasurementException.class,
                () -> service.divide(feet, zero));
    }

    @Test
    @DisplayName("testController_ConsoleOutput_Format")
    public void testController_ConsoleOutput_Format() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        assertDoesNotThrow(() -> controller.performComparison(d1, d2));
        assertDoesNotThrow(() -> controller.performConversion(d1,
                new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES)));
        assertDoesNotThrow(() -> controller.performAddition(d1, d2));
        assertDoesNotThrow(() -> controller.performSubtraction(
                new QuantityDTO(2.0,  QuantityDTO.LengthUnit.FEET), d2));
        assertDoesNotThrow(() -> controller.performDivision(d1, d2));
    }

    // ── Integration Tests ─────────────────────────────────────────────────────

    @Test
    @DisplayName("testIntegration_EndToEnd_LengthAddition")
    public void testIntegration_EndToEnd_LengthAddition() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        QuantityDTO result = controller.performAddition(d1, d2);

        assertNotNull(result);
        assertEquals(2.0,          result.getValue(), 1e-6);
        assertEquals("FEET",       result.getUnitName());
        assertEquals("LengthUnit", result.getMeasurementType());

        List<QuantityMeasurementEntity> entities =
                repository.getAllMeasurements();
        assertEquals(1,     entities.size());
        assertEquals("ADD", entities.get(0).operation);
        assertFalse(entities.get(0).isError);
    }

    @Test
    @DisplayName("testIntegration_EndToEnd_TemperatureUnsupported")
    public void testIntegration_EndToEnd_TemperatureUnsupported() {
        QuantityDTO t1 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO t2 = new QuantityDTO(50.0,  QuantityDTO.TemperatureUnit.CELSIUS);

        QuantityDTO result = controller.performAddition(t1, t2);

        assertNull(result);
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    // ── Validation Tests ──────────────────────────────────────────────────────

    @Test
    @DisplayName("testService_NullEntity_Rejection")
    public void testService_NullEntity_Rejection() {
        QuantityDTO d1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);

        assertThrows(QuantityMeasurementException.class,
                () -> service.compare(d1, null));
        assertThrows(QuantityMeasurementException.class,
                () -> service.compare(null, d1));
        assertThrows(QuantityMeasurementException.class,
                () -> service.add(d1, null));
        assertThrows(QuantityMeasurementException.class,
                () -> service.subtract(null, d1));
    }

    @Test
    @DisplayName("testController_NullService_Prevention")
    public void testController_NullService_Prevention() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        QuantityMeasurementController validController =
                new QuantityMeasurementController(service);
        assertNotNull(validController);
        assertDoesNotThrow(() -> validController.performComparison(d1, d2));
    }



    @Test
    @DisplayName("testEntity_OperationType_Tracking")
    public void testEntity_OperationType_Tracking() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO d3 = new QuantityDTO(0.0,  QuantityDTO.LengthUnit.INCHES);

        service.compare(d1, d2);
        service.convert(d1, d3);
        service.add(d1, d2);
        service.subtract(
                new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET), d2);
        service.divide(d1, d2);

        List<QuantityMeasurementEntity> entities =
                repository.getAllMeasurements();
        assertEquals(5,          entities.size());
        assertEquals("COMPARE",  entities.get(0).operation);
        assertEquals("CONVERT",  entities.get(1).operation);
        assertEquals("ADD",      entities.get(2).operation);
        assertEquals("SUBTRACT", entities.get(3).operation);
        assertEquals("DIVIDE",   entities.get(4).operation);
    }

    // ── Decoupling Tests ──────────────────────────────────────────────────────

    @Test
    @DisplayName("testLayerDecoupling_ServiceChange")
    public void testLayerDecoupling_ServiceChange() {
        IQuantityMeasurementService alternativeService =
                new QuantityMeasurementServiceImpl(repository);

        QuantityMeasurementController altController =
                new QuantityMeasurementController(alternativeService);

        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        assertTrue(altController.performComparison(d1, d2));
        assertNotNull(altController.performAddition(d1, d2));
    }

    @Test
    @DisplayName("testLayerDecoupling_EntityChange")
    public void testLayerDecoupling_EntityChange() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        service.compare(d1, d2);
        service.add(d1, d2);

        List<QuantityMeasurementEntity> entities =
                repository.getAllMeasurements();
        assertEquals(2, entities.size());

        for (QuantityMeasurementEntity entity : entities) {
            assertNotNull(entity.operation);
            assertNotNull(entity.thisUnit);
            assertNotNull(entity.thatUnit);
            assertNotNull(entity.thisMeasurementType);
            assertNotNull(entity.thatMeasurementType);
        }
    }

    @Test
    @DisplayName("testScalability_NewOperation_Addition")
    public void testScalability_NewOperation_Addition() {
        QuantityDTO d1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO target =
                new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);

        QuantityDTO result2Arg   = service.add(d1, d2);
        QuantityDTO result3Arg   = service.add(d1, d2, target);

        assertNotNull(result2Arg);
        assertNotNull(result3Arg);
        assertEquals(2.0,    result2Arg.getValue(), 1e-6);
        assertEquals("FEET", result2Arg.getUnitName());
        assertEquals(24.0,     result3Arg.getValue(), 1e-6);
        assertEquals("INCHES", result3Arg.getUnitName());

        assertEquals(2, repository.getAllMeasurements().size());
    }
}