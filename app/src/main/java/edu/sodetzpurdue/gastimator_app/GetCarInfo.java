package edu.sodetzpurdue.gastimator_app;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Gets the car info using Liberty Mutual's Shine API to create an http request and given
 * parameters
 *
 * @author Ken Sodetz
 * @since 9/30/2017
 */

public class GetCarInfo extends AsyncTask<String, Void, String>{

    //AsyncResponse interface field
    public AsyncResponse delegate = null;

    private final String APIKEY = "A8874x8oBWR0GdYXGccI2tYFFULXur7a";

    /**
     * Default Constructor
     */
    public GetCarInfo() {
    }

    /**
     * Starts the background thread
     * @param strings parameters for the request
     * @return result of the request
     */
    @Override
    protected String doInBackground(String... strings) {
        return shineConnect(strings[0], strings[1], strings[2]);
    }

    /**
     * Pass the result to the main activity once the thread has completed
     * @param result result of the HTTP get request
     */
    @Override
    protected void onPostExecute(String result){
        delegate.processFinish(result);
    }

    /**
     * Sends the HTTP request to get the vehicle data based on given parameters
     * @param make, make of the vehicle
     * @param model, model of the vehicle
     * @param year, year of the vehicle
     * @return responseBody, string response of the API call
     */
    public String shineConnect (String make, String model, String year) {
        String charset = "UTF-8";
        InputStream response = null;
        URLConnection connection;
        String url = "https://apis.solarialabs.com/shine/v1/vehicle-stats/specs?";
        try {
            connection = new URL(url+"make="+make+"&model="+model+"&year="+year+"" +
                    "&apikey="+APIKEY).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            response = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(Scanner scanner = new Scanner(response)) {
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e){
            System.err.println("Response is null");
            return "[]";
        }
    }

    /**
     * Parses City MPG from api call
     * @param apiCallString api string to be parsed
     * @return city mpg as a double
     */
    public double getCityMPG(String apiCallString) {
        String response = apiCallString.substring(apiCallString.indexOf("City_Unadj_Conventional_Fuel"), apiCallString.indexOf("Hwy_Unadj_Conventional_Fuel"));
        response = response.substring(response.indexOf(":") + 1, response.indexOf(","));
        return Double.parseDouble(response);
    }

    /**
     * Parses Highway MPG from api call
     * @param apiCallString api string to be parsed
     * @return highway mpg as a double
     */
    public double getHighwayMPG(String apiCallString) {
        String response = apiCallString.substring(apiCallString.indexOf("Hwy_Unadj_Conventional_Fuel"), apiCallString.indexOf("Air_AspirMethod"));
        response = response.substring(response.indexOf(":") + 1, response.indexOf(","));
        return Double.parseDouble(response);
    }
}
