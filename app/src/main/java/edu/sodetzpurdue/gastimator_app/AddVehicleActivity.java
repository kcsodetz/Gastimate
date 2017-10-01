package edu.sodetzpurdue.gastimator_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{

    EditText makeText, modelText, yearText;
    String make, model;
    int year;
    Button okButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

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


                    }
                }
        );
    }

    @Override
    public void onClick(View view) {

    }
}
