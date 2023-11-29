/**
 * A class just for utility functions used across many classes
 */
public class Utility {

    /**
     * Compare two doubles for approx. equality
     * @param uno first double
     * @param dos second double
     * @return true if uno is the same as dos within a margin of 0.001
     */
    public static boolean compareDoubles(double uno, double dos){
        return Math.abs(uno - dos) <= 0.001;
    }

    // GIVEN CODE
    /**
     * Assume a sensor value is a date if it is greater jan 01, 1970
     * @param sensorDatum the datum which may be a date, datetime, temperature, or humidity
     * @return true if it is a formatted date number
     */
    public static boolean isDate(double sensorDatum){
        return sensorDatum > 19700101.0;
    }

    /**
     * Assume a sensor value is a date if it is greater jan 01, 1970 00:00:00 represented as a double
     * @param sensorDatum the datum which may be a date, datetime, temperature, or humidity
     * @return true if it is a formatted date number
     */
    public static boolean isDateTime(double sensorDatum){
        return sensorDatum > 19700101000000.0;
    }

    /**
     * Converts the double date time format to just the date part by dividing and rounding
     * @param dateTime YYYYMMDDhhmmss.0
     * @return YYYYMMDD.0
     */
    public static double toDate(double dateTime){
        return Math.floor(dateTime / 1000000.0); // convert YYYYMMDDhhmmss -> YYYYMMDD
    }

    /**
     * compares two YYYYMMDD.0 for equality
     * @param date1 one YYYYMMDD.0
     * @param date2 another YYYYMMDD.0
     * @return true if they are within some error tolerance (0.001) of each other
     */
    public static boolean sameDate(double date1, double date2){
        return Math.abs(date1 - date2) < 0.001;
    }

}
