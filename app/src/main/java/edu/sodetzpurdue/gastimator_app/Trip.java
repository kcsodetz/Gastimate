package edu.sodetzpurdue.gastimator_app;

import java.io.Serializable;

/**
 * Created by stefanygo on 10/1/17.
 */

public class Trip implements Serializable{
    private String time;
    private double distance, carHighwayMPG, carCityMPG;

    public Trip (String time, double distance, double carHighwayMPG, double carCityMPG) {
        this.time = time;
        this.distance = distance;
        this.carHighwayMPG = carHighwayMPG;
        this.carCityMPG = carCityMPG;
    }

    public String getTime() {
        return time;
    }

    public double getDistance() {
        return distance;
    }

    public double getCarHighwayMPG() {
        return carHighwayMPG;
    }

    public double getCarCityMPG() {
        return carCityMPG;
    }
}
