package edu.sodetzpurdue.gastimator_app;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DestinationActivity extends AppCompatActivity {

    private EditText origin, destination;
    Button gastimateBtn;
    String originString;
    String destinationString;
    String response;
    Trip trip;
    public final int NO_ORIGIN = 0;
    public final int NO_DESTINATION = 1;
    public final int SUCCESS = 2;
    public final int DEFAULT = 3;
    public final int NO_CONNECTION = 4;
    int timeHours, timeMin;
    double distance;
    GetDistance getDistance = new GetDistance();

    /**
     * onCreate Method
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.setTitle(getString(R.string.originDestination));
        final Intent intent = getIntent();
        final Car car = (Car)intent.getSerializableExtra("car");
//        System.out.println("Highway: " +car.getHwy());
        gastimateBtn = (Button)findViewById(R.id.gastimateButton);
        gastimateBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method
             * @param view current view
             */
            @Override
            public void onClick(View view) {
                origin = (EditText)findViewById(R.id.editText);
                destination = (EditText)findViewById(R.id.editText2);
                originString = origin.getText().toString();
                destinationString = destination.getText().toString();
                if(originString.equals("")) {
                    messageToast(NO_ORIGIN);
                }
                else if(destinationString.equals("")) {
                    messageToast(NO_DESTINATION);
                }
                else if(destinationString.length() < 5 || originString.length() < 5){
                    messageToast(DEFAULT);
                }
                else{
                    response = getDistance.googleMapsConnect(originString, destinationString);
                    if(response.contains("INVALID_REQUEST") || response.contains("ZERO_RESULTS") || response.contains("NOT_FOUND")){
                        messageToast(DEFAULT);
                    }
                    else if (response.equals("EMPTY")){
                        messageToast(NO_CONNECTION);
                    }
                    else {
                        messageToast(SUCCESS);
                        timeHours = getDistance.parseTime(response, 1);
                        timeMin = getDistance.parseTime(response, 2);
                        String time = timeHours+" hour(s) and "+timeMin+" minutes";
                        distance = getDistance.parseDistance(response);
                        System.out.println(distance);
                        trip = new Trip(time, distance, car.getHwy(), car.getCity());
                        Intent intentNext = new Intent(DestinationActivity.this, GastimatorActivity.class);
                        intentNext.putExtra("trip", trip);
                        startActivity(intentNext);
                    }
                }
            }
        });
    }

    /**
     * Handles toasts for missing inputs
     * @param input missing input
     */
    public void messageToast(int input){
        switch (input){
            case NO_ORIGIN:
                Toast.makeText(this, "You did not enter an Origin", Toast.LENGTH_SHORT).show();
                break;
            case NO_DESTINATION:
                Toast.makeText(this, "You did not enter a Destination", Toast.LENGTH_SHORT).show();
                break;
            case SUCCESS:
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
                break;
            case DEFAULT:
                Toast.makeText(this, "Origin and/or Destination not found", Toast.LENGTH_SHORT).show();
                break;
            case NO_CONNECTION:
                Toast.makeText(this, R.string.internetConnection, Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                break;
        }
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
