package quantityMeasurement;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import quantityMeasurement.model.QuantityMeasurementEntity;
import quantityMeasurement.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementCacheRepository
        implements IQuantityMeasurementRepository {

    private static QuantityMeasurementCacheRepository instance;


    private static final String FILE_NAME = "quantity_measurement_repo.ser";
    private List<QuantityMeasurementEntity> cache;
    private QuantityMeasurementCacheRepository() {
        cache = new ArrayList<>();
        loadFromDisk(); 
    }

    public static QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned");
    }


    @Override
    public void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
        saveToDisk(entity);
    }


    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return new ArrayList<>(cache); 
    }


    private void saveToDisk(QuantityMeasurementEntity entity) {
        File file = new File(FILE_NAME);
        boolean fileExists = file.exists() && file.length() > 0;

        try (ObjectOutputStream oos = fileExists
                ? new AppendableObjectOutputStream(new FileOutputStream(file, true))
                : new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(entity);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Error saving entity to disk: " + e.getMessage());
        }
    }

    private void loadFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) return;

        try (ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    QuantityMeasurementEntity entity =
                            (QuantityMeasurementEntity) ois.readObject();
                    cache.add(entity);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    System.err.println(
                            "Error loading entity from disk: " + e.getMessage());
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println(
                    "Error opening file for reading: " + e.getMessage());
        }
    }


    private static class AppendableObjectOutputStream
            extends ObjectOutputStream {


        public AppendableObjectOutputStream(OutputStream out)
                throws IOException {
            super(out);
        }


        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }


    public static void main(String[] args) {
        System.out.println("QuantityMeasurementCacheRepository class");

        QuantityMeasurementCacheRepository repo =
                QuantityMeasurementCacheRepository.getInstance();
        System.out.println("Total measurements: " +
                repo.getAllMeasurements().size());
    }
}
