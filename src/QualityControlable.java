/** An interface asked for by the city civil engineers after too many people got bad quality vegetables due to mold (bad temp and humidity settings) */
public interface QualityControlable {

    /**
     * computes the current percentage of non-datetime sensor values that are -999.0s
     * @return a percent value between 0.0 and 100.0 inclusive
     */
    double percentError();





    
}
