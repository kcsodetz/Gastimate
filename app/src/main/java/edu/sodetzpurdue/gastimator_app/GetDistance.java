package edu.sodetzpurdue.gastimator_app;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Gets the distance of the trip using the Google Maps API
 *
 * @author Shiv Paul
 * @author Ken Sodetz
 * @since 9/30/2017
 */

public class GetDistance  extends AsyncTask<String, Void, String> {
    //AsyncResponse interface field
    public AsyncResponse delegate = null;

    // TODO: Change to internal file
    public final String APIKEY = "AIzaSyD7GjH80EBchoi53fNvVRWGhBrWaPGP_iw";

    /**
     * Default constructor
     */
    public GetDistance(){
    }

    /**
     * Starts the background thread
     * @param strings parameters for the request
     * @return result of the request
     */
    @Override
    protected String doInBackground(String... strings) {
        return googleMapsConnect(strings[0], strings[1]);
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
     * Connects to the api and gets the response string
     * @param origin starting point string
     * @param dest destination string
     * @return responseBody, string response of the API call
     */
    public  String googleMapsConnect(String origin, String dest) {
        InputStream response = null;
        URLConnection connection;
        String link = "https://maps.googleapis" +
                ".com/maps/api/distancematrix/json?units=imperial&";
        try {
            connection = new URL(link + "origins=" + origin + "&destinations=" + dest + "&key=" +
                    APIKEY).openConnection();
            response = connection.getInputStream();
        } catch (IOException io) {
            io.printStackTrace();
        }
        try(Scanner scanner = new Scanner(response)) {
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            return "EMPTY";
        }
    }

    /**
     * Parses the distance given by Google's API
     * @param APIReturn, string returned by google's api
     * @return parsed, double containing number of miles between points
     */
    public double parseDistance(String APIReturn) {
        String distance = APIReturn.substring(APIReturn.indexOf("distance") + 42,
                APIReturn.indexOf(" mi"));
        distance = distance.replace(",", "");
        return Double.parseDouble(distance);
    }

    /**
     * Parses the time of the string returned by Google's API
     * @param APIReturn, string returned by google's api
     * @param hm, 1 if hours, 2 if minutes
     * @return parsed, double containing total time needed for travel
     */
    public int parseTime(String APIReturn, int hm) {
        String time0 = APIReturn.substring(APIReturn.lastIndexOf("duration"),
                APIReturn.lastIndexOf("status"));
        String time1 = time0.substring(time0.lastIndexOf("value") + 9, time0.length());
        String time = "";
        for (int i = 0; i < time1.length(); i++) {
            if (time1.charAt(i) <= '9' && time1.charAt(i) >= '0') {
                time += time1.charAt(i);
            }
        }

        int seconds = Integer.parseInt(time);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        minutes = minutes - hours * 60;

        if (hm == 1) {
            return hours;
        } else if (hm == 2) {
            return minutes;
        } else {
            return 0;
        }
    }
}