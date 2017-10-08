package edu.sodetzpurdue.gastimator_app;

import java.io.Serializable;

/**
 * Trip object
 * @author stefanygo, Ken Sodetz
 * @since 10/1/17
 */

public class Trip implements Serializable{
    private String time;
    private double distance, carHighwayMPG, carCityMPG;

    /**
     * Default Constructor
     * @param time time to destination
     * @param distance distance to destination
     * @param carHighwayMPG highway fuel economy, measured in miles per gallon
     * @param carCityMPG city fuel economy, measured in miles per gallon
     */
    public Trip (String time, double distance, double carHighwayMPG, double carCityMPG) {
        this.time = time;
        this.distance = distance;
        this.carHighwayMPG = carHighwayMPG;
        this.carCityMPG = carCityMPG;
    }

    /**
     * Public getter for time
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * Public getter for distance
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Public getter for carHighwayMPG
     * @return carHighwayMPG
     */
    public double getCarHighwayMPG() {
        return carHighwayMPG;
    }

    /**
     * Public getter for carCityMPG
     * @return carCityMPG
     */
    public double getCarCityMPG() {
        return carCityMPG;
    }
}
