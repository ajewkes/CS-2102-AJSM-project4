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

}
