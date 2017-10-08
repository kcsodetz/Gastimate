package edu.sodetzpurdue.gastimator_app;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity to add a vehicle
 *
 * @author Ken Sodetz
 * @since 9/30/17
 */
public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{

    EditText makeText, modelText, yearText;
    String make;
    String model;
    String yearString;
    String response;
    double hwy, city;
    Button okButton;
    public final int NO_MODEL = 0;
    public final int NO_MAKE = 1;
    public final int NO_YEAR = 2;
    public final int VEHICLE_DOES_NOT_EXIST = 3;
    public final int SUCCESS = 4;
    GetCarInfo getCarInfo = new GetCarInfo();
    Car newCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.setTitle("Add a Vehicle");
        okButton = (Button)findViewById(R.id.okButton);
        okButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        makeText = (EditText)findViewById(R.id.makeText);
                        modelText = (EditText)findViewById(R.id.modelText);
                        yearText = (EditText)findViewById(R.id.yearText);
                        make = makeText.getText().toString();
                        model = modelText.getText().toString();
                        make = make.trim();
                        yearString = yearText.getText().toString();
                        if(make.equals("")) {
                            messageToast(NO_MAKE);
                        }
                        else if(model.equals("")) {
                            messageToast(NO_MODEL);
                        }
                        else if(yearString.equals("")) {
                            messageToast(NO_YEAR);
                        }
                        else{
                            response = getCarInfo.shineConnect(make, model, yearString);
                            if (response.equals("[]")){
                                messageToast(VEHICLE_DOES_NOT_EXIST);
                            }
                            else {
                                messageToast(SUCCESS);
                                hwy = getCarInfo.getHighwayMPG(response);
                                city = getCarInfo.getCityMPG(response);
                                newCar = new Car(make,model,yearString, hwy, city);
                                Intent intent = new Intent(AddVehicleActivity.this, VehicleListActivity.class);
                                intent.putExtra("car", newCar);
                                startActivity(intent);
                            }
                        }
                    }
                }
        );
    }

    /**
     * Handles toasts for missing inputs
     * @param input missing input
     */
    public void messageToast(int input){
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
                Toast.makeText(this, "The vehicle "+make+" "+model+" "+yearString+" does not exist" +
                                "or cannot be found.",
                        Toast.LENGTH_SHORT).show();
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
     * @param view view to switch
     */
    @Override
    public void onClick(View view) {

    }
}
