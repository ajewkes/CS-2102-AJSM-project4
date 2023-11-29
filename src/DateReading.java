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
     * @return the temperatures
     */
    public ArrayList<Double> getTemps() {
        return temps;
    }


    /**
     * Gets the humidities
     * @return the humidity readings
     */
    public ArrayList<Double> getHums() {
        return hums;
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

}