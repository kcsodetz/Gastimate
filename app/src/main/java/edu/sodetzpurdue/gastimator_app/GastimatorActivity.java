package edu.sodetzpurdue.gastimator_app;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class GastimatorActivity extends AppCompatActivity {
    TextView gasText, timeText, messageText, costText;

    /**
     * onCreate method
     * @param savedInstanceState savedInstanceState
     */
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

        if(distance > 40) {
            gasReq = distance/CityMPG;
        }
        else {
            gasReq = distance/HighwayMPG;
        }

        double cost = 2.51 * gasReq;

        String distanceString = "This trip is " + distance + " miles! Stay safe and fuel up!";
        String gasString = String.format("%.2f Gallons",gasReq);
        String costString = String.format("$ %.2f",cost);
        gasText.setText(gasString);
        timeText.setText(time);
        messageText.setText(distanceString);
        costText.setText(costString);
    }

    /**
     * Create and inflate local menu
     * @param menu menu to be inflated
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * The onOptionsItemSelected method gets the selected item from the spinner
     * @param item if the menu item selected, of type MenuItem
     * @return true
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_action:
                startActivity(new Intent(this, VehicleListActivity.class));
                return true;
            default:
                return true;
        }
    }
}
