package edu.sodetzpurdue.gastimator_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class GastimatorActivity extends AppCompatActivity {
    TextView gasText, timeText, messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastimator);
        Intent intent = getIntent();
        Trip trip = (Trip)intent.getSerializableExtra("trip");
        gasText = (TextView) findViewById(R.id.gasD);
        timeText = (TextView) findViewById(R.id.timeS);

        double distance = trip.getDistance();
        double gasReq;
        double CityMPG = trip.getCarCityMPG();
        double HighwayMPG = trip.getCarHighwayMPG();
        String time = trip.getTime();

        if(distance>40)
        {
            gasReq = distance/CityMPG;
        }
        else
        {
            gasReq = distance/HighwayMPG;
        }

        String distanceString = "This trip is " + distance + " miles!";
        String gasString = "You need " + gasReq + " gallons of gas for the trip!!";
        String timeString = "You need " + time + " to get to your Destination!";

        gasText.setText(gasString);
        timeText.setText(timeString);
        messageText.setText(distanceString);


    }
}
