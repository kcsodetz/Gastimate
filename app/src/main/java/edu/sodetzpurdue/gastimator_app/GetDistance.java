package edu.sodetzpurdue.gastimator_app;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * {insert meaningful descrition here}
 *
 * @author Ken Sodetz
 * @since 9/30/2017
 */

public class GetDistance {
    public final String APIKEY = "AIzaSyD7GjH80EBchoi53fNvVRWGhBrWaPGP_iw";

    /**
     * Default constructor
     */
    public GetDistance(){

    }

    /**
     * @return origin, string containing name of origin place with " " replaced with "+"
     */
//    public String getOrigin()
//    {
//        Scanner input = new Scanner(System.in);
//        System.out.print("Enter origin: ");
//        String origin0 = input.nextLine();
//        String origin = origin0.replace(" ","+");
//        return origin;
//    }

    /**
     * @return dest, string containing name of destination place with " " replaced with "+"
     */
//    public String getDestination()
//    {
//        Scanner input = new Scanner(System.in);
//        System.out.print("Enter destination: ");
//        String dest0 = input.nextLine();
//        String dest = dest0.replace(" ","+");
//        return dest;
//    }

    /**
     * Connects to the api and gets the response string
     * @param origin starting point string
     * @param dest destination string
     * @return responseBody, string response of the API call
     */
    public  String googleMapsConnect(String origin, String dest)
    {
        InputStream response = null;
        URLConnection connection;
        String link = "https://maps.googleapis" +
                ".com/maps/api/distancematrix/json?units=imperial&";
        try
        {
            connection = new URL(link+"origins="+origin+"&destinations="+dest+"&key="+APIKEY).openConnection();
            response = connection.getInputStream();
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
        try(Scanner scanner = new Scanner(response))
        {
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e){
            return "EMPTY";
        }
    }


    /**
     * @param APIReturn, string returned by google's api
     * @return parsed, double containing number of miles between points
     */
    public double parseDistance(String APIReturn)
    {
        //System.out.println(APIReturn);
        String distance = APIReturn.substring(APIReturn.indexOf("distance")+42, APIReturn.indexOf(" mi"));
        distance = distance.replace(",","");
        return Double.parseDouble(distance);
    }

    /**
     * @param APIReturn, string returned by google's api
     * @param hm, 1 if hours, 2 if minutes
     * @return parsed, double containing total time needed for travel
     */
    public int parseTime(String APIReturn, int hm)
    {
        //System.out.println(APIReturn);
        String time0 = APIReturn.substring(APIReturn.lastIndexOf("duration"), APIReturn.lastIndexOf("status"));
        //System.out.println(time0);
        String time1 = time0.substring(time0.lastIndexOf("value")+9, time0.length());
        String time ="";
        for(int i=0; i<time1.length(); i++){
            if (time1.charAt(i)<='9' && time1.charAt(i)>='0'){
                //System.out.println(time1.charAt(i));
                time+=time1.charAt(i);
            }
        }

        int seconds = Integer.parseInt(time);
        int minutes = seconds/60;
        int hours = minutes/60;
        minutes = minutes - hours*60;

        if(hm==1)
        {
            return hours;
        }
        else if(hm==2)
        {
            return minutes;
        }
        else
        {
            return 0;
        }
    }
}
