package edu.sodetzpurdue.gastimator_app;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Activity to add a vehicle
 *
 * @author Ken Sodetz
 * @since 9/30/17
 */
public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener,
        AsyncResponse{


    GetCarInfo getCarInfo = new GetCarInfo();

    EditText makeText, modelText, yearText;
    String make, model, yearString, response;
    Button okButton;
    ProgressBar progressBar;
    int year;
    double hwy, city;
    public final int NO_MODEL = 0;
    public final int NO_MAKE = 1;
    public final int NO_YEAR = 2;
    public final int VEHICLE_DOES_NOT_EXIST = 3;
    public final int SUCCESS = 4;
    public final int INVALID_YEAR = 5;
    Car newCar;
    int yearCurrent = Calendar.getInstance().get(Calendar.YEAR);

    /**
     * Override method to get the result of getCarInfo
     * @param output the result from getCarInfo
     */
    @Override
    public void processFinish(Object output) {
        progressBar.setVisibility(View.GONE);
        response = (String)output;
        postResponseSet();
    }

   //@Override
    public void processFinishDouble(Double[] output) {
        //does nothing
    }

    /**
     * onCreate Method
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        this.setTitle("Add a Vehicle");
        progressBar = (ProgressBar)findViewById(R.id.progressBarAddVehicle);

        final AsyncResponse localDelegate = this;

//        getCarInfo.delegate = this;

        okButton = (Button)findViewById(R.id.okButton);
        okButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        okButton.setEnabled(false);
                        makeText = (EditText)findViewById(R.id.makeText);
                        modelText = (EditText)findViewById(R.id.modelText);
                        yearText = (EditText)findViewById(R.id.yearText);
                        make = makeText.getText().toString();
                        model = modelText.getText().toString();
                        make = make.trim();
                        yearString = yearText.getText().toString();
                        if(!yearString.equals("")){
                            year = Integer.parseInt(yearString);
                        }
                        if(make.equals("")) { //check if make is empty
                            messageToast(NO_MAKE);
                        }
                        else if(model.equals("")) { //check if model is empty
                            messageToast(NO_MODEL);
                        }
                        else if(yearString.equals("")) { //check if year is empty
                            messageToast(NO_YEAR);
                        }
                        else if(!(year >= 1885 && year <= yearCurrent+1)) { //check for valid year
                            messageToast(INVALID_YEAR);
                        }
                        else{ //no inputs were invalid
                            getCarInfo = new GetCarInfo();
                            getCarInfo.delegate = localDelegate;
                            getCarInfo.execute(make, model, yearString);
                        }
                    }
                }
        );
    }

    /**
     * Method to execute after thread has completed
     */
    public void postResponseSet() {
        if (response.equals("[]")){ //vehicle does not exist or connection issue
            progressBar.setVisibility(View.GONE);
            messageToast(VEHICLE_DOES_NOT_EXIST);
        }
        else { //vehicle found
            messageToast(SUCCESS);
            hwy = getCarInfo.getHighwayMPG(response);
            city = getCarInfo.getCityMPG(response);
            newCar = new Car(make,model,yearString, hwy, city);
            Intent intent = new Intent(AddVehicleActivity.this,
                    VehicleListActivity.class);
            intent.putExtra("car", newCar);
            startActivity(intent);
        }
    }


    /**
     * Handles toasts for missing inputs
     * @param input missing input
     */
    public void messageToast(int input){
        progressBar.setVisibility(View.GONE);
        okButton.setEnabled(true);
        switch (input){
            case NO_MAKE:
                Toast.makeText(this, "You did not enter a Make", Toast.LENGTH_SHORT).show();
                break;
            case NO_MODEL:
                Toast.makeText(this, "You did not enter a Model", Toast.LENGTH_SHORT).show();
                break;
            case NO_YEAR:
                Toast.makeText(this, "You did not enter a Year", Toast.LENGTH_SHORT).show();
                break;
            case VEHICLE_DOES_NOT_EXIST:
                Toast.makeText(this, "The vehicle "+make+" "+model+" "+yearString+" does not " +
                                "exist or cannot be found.",
                        Toast.LENGTH_SHORT).show();
                break;
            case INVALID_YEAR:
                Toast.makeText(this, "Invalid Year, must be between 1885 and " + (yearCurrent+1),
                        Toast.LENGTH_LONG).show();
                break;
            case SUCCESS:
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * On Click handler
     * @param view view
     */
    @Override
    public void onClick(View view) {
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