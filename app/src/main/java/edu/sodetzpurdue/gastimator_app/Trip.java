package edu.sodetzpurdue.gastimator_app;

/**
 * Created by stefanygo on 10/1/17.
 */

public class Trip {
    private String time;
    private double distance;

    public Trip(String time, double distance) {
        this.time = time;
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public double getDistance() {
        return distance;
    }
}
