package quantityMeasurement.repository;

import java.util.List;

import quantityMeasurement.model.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {
	void save(QuantityMeasurementEntity entity) ;
	List<QuantityMeasurementEntity> getAllMeasurements();
	public static void main(String[] args) {
		System.out.println("IQuantityMeasurementRepository Interface");
	}
	
}
