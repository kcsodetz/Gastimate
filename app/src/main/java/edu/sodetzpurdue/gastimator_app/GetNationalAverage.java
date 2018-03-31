package edu.sodetzpurdue.gastimator_app;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Gets the national average for unleaded and diesel fuel
 *
 * @author Ken Sodetz
 * @since 10/10/2017
 */

public class GetNationalAverage extends AsyncTask<Void, Void, Double[]> {

    private double diesel, regular, premium, midgrade, electric, e85;

    public AsyncResponse delegate = null;

    /**
     * Default constructor
     */
    public GetNationalAverage() {
    }

    /**
     * Get the HTTP request in the background
     * @param params parameters
     * @return collection of prices
     */
    @Override
    protected Double[] doInBackground(Void... params) {
        return requestNatlAvg();
    }

    @Override
    protected void onPostExecute(Double[] result){
            delegate.processFinish(result);
    }

    /**
     * Use fueleconomy.gov to get the national average for some different fuel sources
     * @return collection of prices
     */
    public Double[] requestNatlAvg() {
        String charset = "UTF-8";
        InputStream response = null;
        URLConnection connection;
        String url = "http://www.fueleconomy.gov/ws/rest/fuelprices";
        try {
            connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            response = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(Scanner scanner = new Scanner(response)) {
            String parsed = scanner.useDelimiter("\\A").next();
            return parseNatlAvg(parsed);
        }
    }

    /**
     * Gets the national average for unleaded gasoline, diesel, electric, etc
     * @param response String to be parsed
     * @return collection of the types
     */
    private Double[] parseNatlAvg(String response) {
        Double[] collection = new Double[6];
        diesel = Double.parseDouble(response.substring(response.indexOf("diesel") + 7,
                response.indexOf("</diesel>")));
        collection[0] = diesel;
        e85 = Double.parseDouble(response.substring(response.indexOf("e85") + 4,
                response.indexOf("</e85>")));
        collection[1] = e85;
        electric = Double.parseDouble(response.substring(response.indexOf("electric") + 9,
                response.indexOf("</electric>")));
        collection[2] = electric;
        midgrade = Double.parseDouble(response.substring(response.indexOf("midgrade") + 9,
                response.indexOf("</midgrade>")));
        collection[3] = midgrade;
        regular = Double.parseDouble(response.substring(response.indexOf("regular") + 8,
                response.indexOf("</regular>")));
        collection[4] = regular;
        premium = Double.parseDouble(response.substring(response.indexOf("premium") + 8,
                response.indexOf("</premium>")));
        collection[5] = premium;
        return collection;
    }
}