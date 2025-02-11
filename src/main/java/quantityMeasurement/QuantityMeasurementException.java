package quantityMeasurement;


public class QuantityMeasurementException extends RuntimeException {

   
    public QuantityMeasurementException(String message) {
        super(message);
    }
    public QuantityMeasurementException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void main(String[] args) {
        try {
            throw new QuantityMeasurementException(
                    "Input quantity cannot be null");
        } catch (QuantityMeasurementException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        try {
            try {
                throw new IllegalArgumentException(
                        "Quantities cannot be null");
            } catch (IllegalArgumentException e) {
                throw new QuantityMeasurementException(
                        "Invalid quantity input: " + e.getMessage(), e);
            }
        } catch (QuantityMeasurementException e) {
            System.out.println("Caught exception: " + e.getMessage());
            System.out.println("Caused by: "       + e.getCause().getMessage());
        }
    }
}