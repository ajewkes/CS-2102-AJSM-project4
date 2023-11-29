import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Uses hash maps to store data
 */
public class HashMapStrategy implements  ParsedDataStrategy{

    /**
     * A list of date readings
     */
    private HashMap<Double, DateReading> dateReadings;
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
     * Constructs a new HashMapStrategy
     */
    public HashMapStrategy(){
        temps = new ArrayList<Double>();
        hums = new ArrayList<Double>();
        dateReadings = new HashMap<Double, DateReading>();
        this.numErrs = 0;
    }


    /**
     * Processes data
     *
     * @param data raw data
     */
    @Override
    public void processData(List<Double> data) {
        dateReadings.putAll(cleanData(parseData(data)));
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

    /**
     * computes the current percentage of non-datetime sensor values that are -999.0s
     *
     * @return a percent value between 0.0 and 100.0 inclusive
     */
    @Override
    public double percentError() {
        return (numErrs / (double)(temps.size() + hums.size() + numErrs)) * 100.0;
    }

    /**
     * Gets the first reading that correspond to a particular date, or an empty reading
     * @param onDate a date in the format //TODO
     */
    private DateReading getDateReadings(double onDate) {
        DateReading d = dateReadings.get(onDate);

        if (d == null) {
            return new DateReading(0, new ArrayList<Double>(), new ArrayList<Double>());
        }
        return d;
    }

    /**
     * Checks if a date reading for a certain date already exists in a list of DateReadings
     * @param date the date
     * @param ndr a list of DateReadings
     * @return true if a corresponding DateReading already exists, false otherwise
     */
    public Boolean doesDateReadingExist(double date, HashMap<Double, DateReading> ndr){
        return (ndr.get(date) != null);
    }

    /**
     * Flattens the readings in dateReadings into two sorted fields, temps and hums
     */
    private void flattenReadings(){
        for (DateReading d : dateReadings.values()){
            temps.addAll(d.getTemps());
            hums.addAll(d.getHums());
        }
        temps.sort(Double::compare);
        hums.sort(Double::compare);
    }

    /**
     * Cleans data once parsed
     * @param dateReadings a list of date readings
     * @return a new, cleaned list of date readings
     */
    private HashMap<Double, DateReading> cleanData(HashMap<Double, DateReading> dateReadings){
        for (DateReading dr: dateReadings.values()){
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
     * Parses data from sensor
     * @param data provided by sensor
     * @return reformatted list of data
     */
    private HashMap<Double, DateReading> parseData(List<Double> data){
        HashMap<Double, DateReading> ndr = new HashMap<Double, DateReading>();
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
            ndr.put(date, dr);
        }
        return ndr;
    }


}