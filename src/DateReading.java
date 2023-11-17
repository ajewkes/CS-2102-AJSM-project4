import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * A DTO for readings on a specific date
 */
public class DateReading {

    /**
     * The given date in the format yyyymmdd
     */
    private double date;

    /**
     * A list of temperature readings
     */
    private ArrayList<Double> temps;

    /**
     * A list of humidity readings
     */
    private ArrayList<Double> hums;

    /**
     * Constructs a date reading
     * @param date the date
     * @param temps the temperatures to go in
     * @param hums the humidities to go in
     */
    public DateReading(double date, ArrayList<Double> temps, ArrayList<Double> hums){
        this.date = date;
        this.temps = temps;
        this.hums = hums;
    }

    /**
     * @return the date in format yyyymmdd
     */
    public double getDate() {
        return date;
    }

    /**
     * Sets the date to date
     * @param date the date in format yyyymmdd
     */
    public void setDate(double date) {
        this.date = date;
    }

    /**
     * @return the temperatures
     */
    public ArrayList<Double> getTemps() {
        return temps;
    }

    /**
     * Sets the temperatures to temps
     * @param temps the temperatures
     */
    public void setTemps(ArrayList<Double> temps) {
        this.temps = temps;
    }

    /**
     * Gets the humidities
     * @return the humidity readings
     */
    public ArrayList<Double> getHums() {
        return hums;
    }

    /**
     * Sets the humidities to hums
     * @param hums the humidities
     */
    public void setHums(ArrayList<Double> hums) {
        this.hums = hums;
    }

    /**
     * Adds t to temps
     * @param t a temperature
     */
    public void addTemp(double t){
        this.temps.add(t);
    }

    /**
     * Adds h to hums
     * @param h a humidity
     */
    public void addHum(double h) {
        this.hums.add(h);
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof DateReading)){
//            return false;
//        }
//        DateReading d = ((DateReading) obj);
//        return compareDoubles(this.date, d.date) &&
//    }

    /**
     * Compare two doubles for approx. equality
     * @param uno first double
     * @param dos second double
     * @return true if uno is the same as dos within a margin of 0.001
     */
    private boolean compareDoubles(double uno, double dos){
        return Math.abs(uno - dos) <= 0.001;
    }
}