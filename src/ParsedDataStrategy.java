import java.util.List;

/**
 * An interface for any data storage strategy
 */
public interface ParsedDataStrategy {

    /**
     * Processes dta
     * @param data raw data
     */
    public void processData(List<Double> data);

    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     *
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     * and the middle humidity of the sorted humidities
     * If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    public TempHumidReading middleReading();

    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     *
     * @param onDate the date which to consider medianReadings for (inclusive) with the format YYYYMMDD.0
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     * and the middle humidity of the sorted humidities
     * If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    public TempHumidReading middleReading(double onDate);


    /**
     * computes the current percentage of non-datetime sensor values that are -999.0s
     * @return a percent value between 0.0 and 100.0 inclusive
     */
    public double percentError();


}
