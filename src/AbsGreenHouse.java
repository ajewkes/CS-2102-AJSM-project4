import jdk.jshell.execution.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * An abstract superclass to provide template methods for performance specific subclasses.
 */
public abstract class AbsGreenHouse {
    /**
     * A list of date readings
     */
    protected ArrayList<DateReading> dateReadings;
    /**
     * A list of sorted temps
     */
    protected ArrayList<Double> temps;
    /**
     * A list of sorted humidities
     */
    protected ArrayList<Double> hums;

    /**
     * Constructs a new abstract greenhoyse
     */
    public AbsGreenHouse(){
        temps = new ArrayList<Double>();
        hums = new ArrayList<Double>();
        dateReadings = new ArrayList<DateReading>();
    }

    /**
     * Parses data from sensor
     * @param data provided by sensor
     * @return reformatted list of data
     */
    public ArrayList<DateReading> parseData(List<Double> data){
        ArrayList<DateReading> ndr = new ArrayList<DateReading>();
        DateReading dr = null;
        for (int i = 0; i < data.size(); i++){
            double date = toDate(data.get(i));
            if (!doesDateReadingExist(date, ndr))
                dr = new DateReading(date, new ArrayList<Double>(), new ArrayList<Double>());
            while (i + 1 < data.size() && !isDateTime(data.get(i+1))){
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
    public Boolean doesDateReadingExist(double date, ArrayList<DateReading> ndr){
        for (DateReading dr : ndr){
            if (Utility.compareDoubles(dr.getDate(), date)){
                return true;
            }
        }
        return false;
    }

    /**
     * Cleans data once parsed
     * @param dateReadings a list of date readings
     * @return a new, cleaned list of date readings
     */
    public ArrayList<DateReading> cleanData(ArrayList<DateReading> dateReadings){
        for (DateReading dr: dateReadings){
            for (int x = dr.getTemps().size()-1; x >= 0; x--) {
                if (Utility.compareDoubles(dr.getTemps().get(x), -999.0)) {
                    dr.getTemps().remove(x);
                }
            }

            for (int x = dr.getHums().size()-1; x >= 0; x--) {
                if (Utility.compareDoubles(dr.getHums().get(x), -999.0)) {
                    dr.getHums().remove(x);
                }
            }
        }
        return dateReadings;
    }

    /**
     * Flattens the readings in dateReadings into two sorted fields, temps and hums
     */
    public void flattenReadings(){
        for (DateReading d : dateReadings){
            temps.addAll(d.getTemps());
            hums.addAll(d.getHums());
        }
        temps.sort(Double::compare);
        hums.sort(Double::compare);
    }

    /**
     * Gets the first reading that correspond to a particular date, or an empty reading
     * @param onDate a date in the format //TODO
     */
    public DateReading getDateReadings(double onDate) {
        for (DateReading d : dateReadings){
            if (Utility.compareDoubles(d.getDate(), onDate)){
                return d;
            }
        }
        return new DateReading(0, new ArrayList<Double>(), new ArrayList<Double>());
    }

    // GIVEN CODE
    /**
     * Assume a sensor value is a date if it is greater jan 01, 1970
     * @param sensorDatum the datum which may be a date, datetime, temperature, or humidity
     * @return true if it is a formatted date number
     */
  public boolean isDate(double sensorDatum){
      return sensorDatum > 19700101.0;
  }

    /**
     * Assume a sensor value is a date if it is greater jan 01, 1970 00:00:00 represented as a double
     * @param sensorDatum the datum which may be a date, datetime, temperature, or humidity
     * @return true if it is a formatted date number
     */
  public boolean isDateTime(double sensorDatum){
      return sensorDatum > 19700101000000.0;
  }

    /**
     * Converts the double date time format to just the date part by dividing and rounding
     * @param dateTime YYYYMMDDhhmmss.0
     * @return YYYYMMDD.0
     */
  public double toDate(double dateTime){
      return Math.floor(dateTime / 1000000.0); // convert YYYYMMDDhhmmss -> YYYYMMDD
  }

    /**
     * compares two YYYYMMDD.0 for equality
     * @param date1 one YYYYMMDD.0
     * @param date2 another YYYYMMDD.0
     * @return true if they are within some error tolerance (0.001) of each other
     */
  public boolean sameDate(double date1, double date2){
      return Math.abs(date1 - date2) < 0.001;
  }
 

}