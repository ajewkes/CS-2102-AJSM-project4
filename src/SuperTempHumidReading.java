/**
 * Represents a humidity and temperature reading with extra features
 */
public class SuperTempHumidReading extends TempHumidReading{
    /**
     * A standard data constructor
     *
     * @param temperature in Fahrenheit
     * @param humidity    in percentage
     */
    public SuperTempHumidReading(double temperature, double humidity) {
        super(temperature, humidity);
    }

    /**
     * Construct a SuperTempHumidReading with default values of -999.0
     * for humidity and temperature
     */
    public SuperTempHumidReading(){
       super(-999.0, -999.0);
    }

    /**
     * Construct a SuperTempHumidReading as copy of thr
     * @param thr The TempHumidReading to copy
     */
    public SuperTempHumidReading(TempHumidReading thr){
        super(thr.temperature, thr.humidity);
    }

    /**
     * Check if objects equal
     * @param o object to compare
     * @return true if the temperature and humidity values of this and o match within 0.001
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof SuperTempHumidReading)){
            return false;
        }

        SuperTempHumidReading s = ((SuperTempHumidReading) o);

        return compareDoubles(this.temperature, s.temperature) && compareDoubles(this.humidity, s.humidity);
    }

    /**
     * Convert object to string
     * if a temperature or humidity is -999 then replace it with Err
     * @return a String in the format "{98.6F;33.4%}"
     */
    @Override
    public String toString(){
        String tempS;
        String humS;

        if (!(compareDoubles(temperature, -999.0))){
             tempS = String.format("%.1f", temperature) + "F";
        } else  tempS = "Err";

        if (!(compareDoubles(humidity, -999.0))){
            humS = String.format("%.1f", humidity) + "%";
        } else  humS = "Err";

        return "{" + tempS + ";" + humS + "}";
    }

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
