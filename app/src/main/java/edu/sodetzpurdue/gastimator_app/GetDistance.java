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
    public String getDestination()
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter destination: ");
        String dest0 = input.nextLine();
        String dest = dest0.replace(" ","+");
        return dest;
    }

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
        assert response != null;
        try(Scanner scanner = new Scanner(response))
        {
            return scanner.useDelimiter("\\A").next();
        }
    }


    /**
     * @param APIReturn, string returned by google's api
     * @return parsed, double containing number of miles between the points
     */
    public double parseAPIReturn(String APIReturn)
    {
        //System.out.println(APIReturn);
        String parsed0 = APIReturn.substring(APIReturn.indexOf("distance")+42, APIReturn.indexOf(" mi"));
        String parsed =  parsed0.replace(",","");
        return Double.parseDouble(parsed);
    }
}
