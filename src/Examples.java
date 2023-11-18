import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class Examples {
    private final GregorianCalendar cal = new GregorianCalendar(2023,Calendar.JANUARY,1);
    public Examples(){

    }

    @Test
    public void timePollDataTest(){
        GreenHouseNursery nursery = new GreenHouseNursery(cal);
        GreenHouseProduce produce  = new GreenHouseProduce(cal);

        List<Double> data = new LinkedList<>();
        data.add(20231106010101.0);
        Random random = new Random();

        for (int i = 0; i < 1000; i++){
            data.add(random.nextDouble() % 100);
        }

        long time1 = System.currentTimeMillis();
        nursery.pollSensorData(data);
        long time2 = System.currentTimeMillis();
        produce.pollSensorData(data);
        long time3 = System.currentTimeMillis();

        long nurseryTime = time2 - time1;
        long produceTime = time3 - time2;

        assertTrue("nursery should be faster than produce", nurseryTime < produceTime);

    }

    @Test
    public void timeMiddleReadingTest(){
        GreenHouseNursery nursery = new GreenHouseNursery(cal);
        GreenHouseProduce produce  = new GreenHouseProduce(cal);

        List<Double> data = new LinkedList<>();
        data.add(20231106010101.0);
        Random random = new Random();

        for (int i = 0; i < 1000; i++){
            data.add(random.nextDouble() % 100);
        }

        long time1 = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            nursery.middleReading();
        }
        long time2 = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            produce.middleReading();
        }
        long time3 = System.nanoTime();

        long nurseryTime = time2 - time1;
        long produceTime = time3 - time2;

        assertTrue("produce should be faster than nursery", nurseryTime > produceTime);
    }

    @Test
    public void testPollAndMiddleNursery(){
        GreenHouseNursery nursery = new GreenHouseNursery(cal);
        List<Double> data = List.of(20231106010101.0,45.5,34.0,46.6,40.0,20231130020202.0,22.2,20.0,35.5,30.0,-999.0,31.0,32.2,-999.0);

        nursery.pollSensorData(data);
        assertEquals(nursery.middleReading(), new SuperTempHumidReading( 35.5,  31.0));
    }

    @Test
    public void testSupTempConZeroParam(){
        SuperTempHumidReading s = new SuperTempHumidReading();

        assertEquals(s.temperature, -999, 0.00001);
        assertEquals(s.humidity, -999, 0.00001);
    }


    @Test
    public void testSupTempConOneParam(){
        SuperTempHumidReading s = new SuperTempHumidReading(new TempHumidReading(10,10));

        assertEquals(s.temperature, 10, 0.00001);
        assertEquals(s.humidity, 10, 0.00001);
    }

    @Test
    public void testSupTempConTwoParam(){
        SuperTempHumidReading s = new SuperTempHumidReading(10, 10);

        assertEquals(s.temperature, 10, 0.00001);
        assertEquals(s.humidity, 10, 0.00001);
    }

    @Test
    public void testSupTempEqualsTrue(){
        SuperTempHumidReading s = new SuperTempHumidReading(10, 10);
        SuperTempHumidReading b = new SuperTempHumidReading(10, 10);

        assertEquals(s, b);
    }

    @Test
    public void testSupTempEqualsFalse(){
        SuperTempHumidReading s = new SuperTempHumidReading(10, 10);
        SuperTempHumidReading b = new SuperTempHumidReading(11, 10);

        assertNotEquals(s, b);
    }

    @Test
    public void testSupTempEqualsNotSameData(){
        SuperTempHumidReading s = new SuperTempHumidReading(10, 10);

        assertNotEquals(s, 1);
    }

    @Test
    public void testSupTempToString(){
        SuperTempHumidReading s = new SuperTempHumidReading(98.6, 33.4);

        assertEquals(s.toString(), "{98.6F;33.4%}");
    }

    @Test
    public void testSupTempToStringBadData(){
        SuperTempHumidReading s = new SuperTempHumidReading(-999, -999);

        assertEquals(s.toString(), "{Err;Err}");
    }

    @Test
    public void testPollSensorDataProduce(){

        List<Double> data = List.of(20231106010101.0, 49.0, 32.0, 45.0, 67.0, 43.0, 57.0);

        GreenHouseProduce g = new GreenHouseProduce(cal);

        g.pollSensorData(data);

        assertEquals(g.middleReading(), new SuperTempHumidReading(45.0, 57.0));

    }

    @Test
    public void testPollSensorDataNursery(){

        List<Double> data = List.of(20231106010101.0, 49.0, 32.0, 45.0, 67.0, 43.0, 57.0);

        GreenHouseNursery g = new GreenHouseNursery(cal);

        g.pollSensorData(data);

        assertEquals(g.middleReading(), new SuperTempHumidReading(45.0, 57.0));

    }

    @Test
    public void testPollSensorDataProduceTwice(){

        List<Double> data = List.of(20231106010101.0, 49.0, 32.0, 45.0, 67.0, 43.0, 57.0);

        GreenHouseProduce g = new GreenHouseProduce(cal);

        g.pollSensorData(data);
        g.pollSensorData(data);

        assertEquals(g.middleReading(), new SuperTempHumidReading(45.0, 57.0));

    }

    @Test
    public void testPollSensorDataNurseryTwice(){

        List<Double> data = List.of(20231106010101.0, 49.0, 32.0, 45.0, 67.0, 43.0, 57.0);

        GreenHouseNursery g = new GreenHouseNursery(cal);

        g.pollSensorData(data);
        g.pollSensorData(data);

        assertEquals(g.middleReading(), new SuperTempHumidReading(45.0, 57.0));

    }

    @Test
    public void testPollSensorDataProduceDate(){

        List<Double> data = List.of(20231106010101.0, 49.0, 32.0, 45.0, 67.0, 43.0, 57.0);

        GreenHouseProduce g = new GreenHouseProduce(cal);

        g.pollSensorData(data);

        assertEquals(g.middleReading(20231106), new SuperTempHumidReading(45.0, 57.0));

    }

    @Test
    public void testPollSensorDataNurseryDate(){

        List<Double> data = List.of(20231106010101.0, 49.0, 32.0, 45.0, 67.0, 43.0, 57.0);

        GreenHouseNursery g = new GreenHouseNursery(cal);

        g.pollSensorData(data);

        assertEquals(g.middleReading(20231106), new SuperTempHumidReading(45.0, 57.0));

    }

    /*
    @Test
    public void testAbsGreenhouseParseData(){
        List<Double> data = List.of(20231106010101.0, 49.0, 32.0, 45.0, 67.0, 43.0, 57.0);
        GreenHouseNursery g = new GreenHouseNursery(cal);
        ArrayList<DateReading> ndr =  g.parseData(data);
        assertEquals(new ArrayList<Double>(List.of(43.0, 45.0, 49.0)), ndr.get(0).getTemps());
        assertEquals(new ArrayList<Double>(List.of(32.0, 57.0, 67.0)), ndr.get(0).getHums());
    }

    @Test
    public void testAbsGreenhouseCleanData(){
        List<Double> data = List.of(20231106010101.0, -999.0, -999.0, 45.0, 67.0, 43.0, 57.0);
        GreenHouseNursery g = new GreenHouseNursery(cal);
        ArrayList<DateReading> ndr = g.cleanData(g.parseData(data));
        assertEquals(new ArrayList<Double>(List.of(43.0, 45.0)), ndr.get(0).getTemps());
        assertEquals(new ArrayList<Double>(List.of(57.0, 67.0)), ndr.get(0).getHums());
    }
    */


}