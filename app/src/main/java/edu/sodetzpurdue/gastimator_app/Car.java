package edu.sodetzpurdue.gastimator_app;

import java.io.Serializable;

/**
 * {insert meaningful descrition here}
 * @author shiva
 * @since 10/1/2017
 */

public class Car implements Serializable{
    private String make, model, year;
    private double hwy, city;

    /**
     * Default Constructor
     * @param make make of the vehicle
     * @param model model of the vehicle
     * @param year year of the vehicle
     * @param hwy highway fuel economy
     * @param city city fuel economy
     */
    public Car(String make, String model, String year, double hwy, double city) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.hwy = hwy;
        this.city = city;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public double getCity() {
        return city;
    }

    public double getHwy() {
        return hwy;
    }
}
