package edu.sodetzpurdue.gastimator_app;

import java.io.Serializable;

/**
 * Car Object
 *
 * @author Ken Sodetz
 * @since 10/1/2017
 */

public class Car implements Serializable {
    private String make, model, year;
    private double hwyMPG, cityMPG;

    /**
     * Default Constructor
     * @param make make of the vehicle
     * @param model model of the vehicle
     * @param year year of the vehicle
     * @param hwyMPG highway fuel economy
     * @param cityMPG city fuel economy
     */
    public Car(String make, String model, String year, double hwyMPG, double cityMPG) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.hwyMPG = hwyMPG;
        this.cityMPG = cityMPG;
    }

    /**
     * Public getter for make
     * @return make
     */
    public String getMake() {
        return make;
    }

    /**
     * Public setter for make
     * @param make make of vehicle
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Public getter for model
     * @return model
     */
    public String getModel() {
        return model;
    }

    /**
     * Public setter for model
     * @param model model of car
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Public getter for year
     * @return year
     */
    public String getYear() {
        return year;
    }

    /**
     * Public getter for city fuel economy
     * @return city
     */
    public double getCity() {
        return cityMPG;
    }

    /**
     * Public getter for highway fuel economy
     * @return highway
     */
    public double getHwy() {
        return hwyMPG;
    }
}
