import java.util.ArrayList;
import java.util.List;

/**
A strategy which uses ArrayLists to store data
 */
public class ArrayListStrategy implements ParsedDataStrategy{

    /**
     * A list of date readings
     */
    private ArrayList<DateReading> dateReadings;
    /**
     * A list of sorted temps
     */
    private ArrayList<Double> temps;
    /**
     * A list of sorted humidities
     */
    private ArrayList<Double> hums;

    /**
     * The number of errors (-999) encountered in the data
     */
    private int numErrs;


    /**
     * Constructs a new ArrayListStrategy
     */
    public ArrayListStrategy(){
        temps = new ArrayList<Double>();
        hums = new ArrayList<Double>();
        dateReadings = new ArrayList<DateReading>();
        this.numErrs = 0;
    }

    /**
     * Processes data
     *
     * @param data raw data
     */
    @Override
    public void processData(List<Double> data) {
        dateReadings.addAll(cleanData(parseData(data)));
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
        DateReading d = getDateReadings(onDate);

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

    //helpers
    /**
     * Cleans data once parsed
     * @param dateReadings a list of date readings
     * @return a new, cleaned list of date readings
     */
    private ArrayList<DateReading> cleanData(ArrayList<DateReading> dateReadings){
        for (DateReading dr: dateReadings){
            for (int x = dr.getTemps().size()-1; x >= 0; x--) {
                if (Utility.compareDoubles(dr.getTemps().get(x), -999.0)) {
                    numErrs ++;
                    dr.getTemps().remove(x);
                }
            }

            for (int x = dr.getHums().size()-1; x >= 0; x--) {
                if (Utility.compareDoubles(dr.getHums().get(x), -999.0)) {
                    numErrs ++;
                    dr.getHums().remove(x);
                }
            }
        }
        return dateReadings;
    }

    /**
     * Flattens the readings in dateReadings into two sorted fields, temps and hums
     */
    private void flattenReadings(){
        for (DateReading d : dateReadings){
            temps.addAll(d.getTemps());
            hums.addAll(d.getHums());
        }
        temps.sort(Double::compare);
        hums.sort(Double::compare);
    }

    /**
     * Parses data from sensor
     * @param data provided by sensor
     * @return reformatted list of data
     */
    private ArrayList<DateReading> parseData(List<Double> data){
        ArrayList<DateReading> ndr = new ArrayList<DateReading>();
        DateReading dr = null;
        for (int i = 0; i < data.size(); i++){
            double date = Utility.toDate(data.get(i));
            if (!doesDateReadingExist(date, ndr))
                dr = new DateReading(date, new ArrayList<Double>(), new ArrayList<Double>());
            while (i + 1 < data.size() && !Utility.isDateTime(data.get(i+1))){
                i++;
                dr.addTemp(data.get(i));
                i++;
                dr.addHum(data.get(i));
            }
            dr.getTemps().sort(Double::compare);
            dr.getHums().sort(Double::compare);
            ndr.add(dr);
        }
        return ndr;
    }

    /**
     * Checks if a date reading for a certain date already exists in a list of DateReadings
     * @param date the date
     * @param ndr a list of DateReadings
     * @return true if a corresponding DateReading already exists, false otherwise
     */
    private Boolean doesDateReadingExist(double date, ArrayList<DateReading> ndr){
        for (DateReading dr : ndr){
            if (Utility.compareDoubles(dr.getDate(), date)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the first reading that correspond to a particular date, or an empty reading
     * @param onDate a date formatted as a double
     */
    private DateReading getDateReadings(double onDate) {
        for (DateReading d : dateReadings){
            if (Utility.compareDoubles(d.getDate(), onDate)){
                return d;
            }
        }
        return new DateReading(0, new ArrayList<Double>(), new ArrayList<Double>());
    }

    /**
     * computes the current percentage of non-datetime sensor values that are -999.0s
     * @return a percent value between 0.0 and 100.0 inclusive
     */
    public double percentError(){
        return (numErrs / (double)(temps.size() + hums.size() + numErrs)) * 100.0;
    }
}
