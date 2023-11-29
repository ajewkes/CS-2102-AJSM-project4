import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * An abstract superclass to provide template methods for performance specific subclasses.
 */
public abstract class AbsGreenHouse implements QualityControlable{

    /**
     * A gregorian calendar
     */
    protected GregorianCalendar calendar;

    /**
     * The parsed data strategy
     */
    protected ParsedDataStrategy parsedDataStrategy;

    /**
     * Raw sensor data
     */
    protected ArrayList<Double> data;

    /**
     * Constructs a new abstract greenhoyse
     */
    public AbsGreenHouse(){
        calendar = new GregorianCalendar();
        parsedDataStrategy = new ArrayListStrategy();
    }

    /**
     * Constructs a new abstract greenhouse with
     * calendar initialized as calendar
     * @param calendar the calendar
     */
    public AbsGreenHouse(GregorianCalendar calendar){
        this.calendar = (GregorianCalendar) calendar.clone();
        parsedDataStrategy = new ArrayListStrategy();
    }


    /**
     * Filters out data that occurs before the current calendar
     * @param data the data to filter
     * @return the filtered data
     */
    public ArrayList<Double> filterData(List<Double> data){
        ArrayList<Double> newData = new ArrayList<Double>();
        for (int i = 0; i < data.size(); i++){
            double time = data.get(i);
            if (Utility.isDateTime(time) && time >= clockAsDatetime()){
                newData.add(data.get(i));
                setClockTo(time);
                while (i + 1 < data.size() && !Utility.isDateTime(data.get(i + 1))){
                    i++;
                    newData.add(data.get(i));
                }
            }
        }
        return newData;
    }

    /**
     * A helper method to convert a gregroian calendar to a HW3 style datetime double
     * @return a HW3 style datetime double
     */
    protected double clockAsDatetime(){
        double year = calendar.get(Calendar.YEAR);
        double month = calendar.get(Calendar.MONTH) + 1;
        double day = calendar.get(Calendar.DAY_OF_MONTH);
        double hour = calendar.get(Calendar.HOUR_OF_DAY);
        double minute = calendar.get(Calendar.MINUTE);
        double second = calendar.get(Calendar.SECOND);
        return second +
                (minute * 100.0) + //shifted 2 decimal places
                (hour * 100.0 * 100.0) + //shifted 4 decimal places
                (day * 100.0 * 100.0 * 100.0) + //shifted 6 decimal places
                (month * 100.0 * 100.0 * 100.0 * 100.0) + //shifted 8 decimal places
                (year * 100.0 * 100.0 * 100.0 * 100.0 * 100.0); //shifted 10 decimal places
    }

    /**
     * Given a datetime as a double, make a java.util.GregorianCalendar object with the
     * appropriate year, month, day of the month, hour of the day, minute, and second.
     *
     * @param datetime a double in the format YYYYMMDDhhmmss.0
     * for example 20231112133045 for the date time Nov 12th 2023 at 1:30:45pm
     */
    private void setClockTo(double datetime) {
        String datetimeStr = String.format("%.0f", datetime);

        int year = Integer.parseInt(datetimeStr.substring(0, 4));
        // Subtract 1 from month because GregorianCalendar months are 0-based
        int month = Integer.parseInt(datetimeStr.substring(4, 6)) - 1;
        int day = Integer.parseInt(datetimeStr.substring(6, 8));
        int hour = Integer.parseInt(datetimeStr.substring(8, 10));
        int minute = Integer.parseInt(datetimeStr.substring(10, 12));
        int second = Integer.parseInt(datetimeStr.substring(12, 14));
        this.calendar = new GregorianCalendar(year, month, day, hour, minute, second);
    }

    /**
     * Handles switching to another strategy
     * @param otherStrategy the strategy to be switched to
     */
    public void setStrategy(ParsedDataStrategy otherStrategy){
        this.parsedDataStrategy = otherStrategy;
        data.clear();
    }

}