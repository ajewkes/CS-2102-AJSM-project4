/**
 * A simple DTO for temperature and humidity sensor data
 */
public class TempHumidReading {

    /**
     * Temperature in Fahrenheit
     */
    protected double temperature;
    /**
     * Humidity as a % from 0.0% to 100.0%
     */
    protected double humidity;

    /**
     * A standard data constructor
     * @param temperature in Fahrenheit
     * @param humidity in percentage
     */
    public TempHumidReading(double temperature, double humidity){
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
