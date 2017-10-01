package edu.sodetzpurdue.gastimator_app;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class GastimatorActivity extends AppCompatActivity {
    TextView gasText, timeText, messageText, costText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastimator);
        Intent intent = getIntent();
        Trip trip = (Trip)intent.getSerializableExtra("trip");
        this.setTitle("Results");
        gasText = (TextView) findViewById(R.id.gasD);
        timeText = (TextView) findViewById(R.id.timeS);
        messageText = (TextView) findViewById(R.id.message);
        costText = (TextView) findViewById(R.id.cost);

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

        double cost = 2.51 * gasReq;

        String distanceString = "This trip is " + distance + " miles! Stay safe and fuel up!";
        String gasString = String.format("%.2f Gallons",gasReq);
        String timeString = time;
        String costString = String.format("$ %.2f",cost);

        gasText.setText(gasString);
        timeText.setText(timeString);
        messageText.setText(distanceString);
        costText.setText(costString);


    }
}
