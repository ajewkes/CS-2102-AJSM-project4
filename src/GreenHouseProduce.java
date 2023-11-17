import java.util.ArrayList;
import java.util.List;

/**
 * A nursery which implements real-time processing
 */
public class GreenHouseProduce extends AbsGreenHouse implements Sensible{

    /**
     * Constructs a green house produce
     */
    public GreenHouseProduce(){
        super();
    }

    /**
     * Reads an ordered sequence of data from the weather sensors to store in the greenhouse
     * When called multiple times, appends the new readings after the current sensor readings
     *
     * @param values An ordered sequence of [datetime, temperature, humidity, temperature, humidity, ..., datetime, temperature, humidity,....]
     *               - a date and time in yyyymmddHHMMSS format. E.g. 20231106183930 for Nov 11, 2023, 6:39:30pm
     *               - temperature is either degrees Fahrenheit or -999 for an error case
     *               - humidity is either % from 0.0 to 100.0 or -999 for an error case
     *               Assume the sensor data always starts with a valid date
     *               The multiple temperature humidity pairs for a single datetime come from different plant sensors
     *               The values may skip dates and times when the sensors are off (you cannot assume that the date/time intervals will be regular)
     *               You *may* assume that the datetimes will be in ascending order
     */
    @Override
    public void pollSensorData(List<Double> values) {
        dateReadings.addAll(cleanData(parseData(values)));
        flattenReadings();
    }

    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     *
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     * and the middle humidity of the sorted humidities
     * If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    @Override
    public TempHumidReading middleReading() {
        if (!temps.isEmpty())
            return new SuperTempHumidReading(temps.get(temps.size()/2), hums.get(hums.size()/2));
        return new SuperTempHumidReading(-999.0, -999.0);
    }

    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     *
     * @param onDate the date which to consider medianReadings for (inclusive) with the format YYYYMMDD.0
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     * and the middle humidity of the sorted humidities
     * If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    @Override
    public TempHumidReading middleReading(double onDate) {
        for (DateReading d : dateReadings){
            if (Utility.compareDoubles(d.getDate(), onDate)){
                ArrayList<Double> temps = d.getTemps();
                ArrayList<Double> hums = d.getHums();
                Double temp = -999.0;
                Double hum = -999.0;
                if (!temps.isEmpty())
                    temp = temps.get(temps.size()/2);
                if (!hums.isEmpty())
                    hum = hums.get(hums.size()/2);

                return new SuperTempHumidReading(temp, hum);
            }
        }
        return new SuperTempHumidReading(-999.0, -999.0);
    }

}
