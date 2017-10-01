package edu.sodetzpurdue.gastimator_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DestinationActivity extends AppCompatActivity {
    private EditText origin, destination;
    Button gastimate;
    String originString, destinationString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        gastimate = (Button)findViewById(R.id.gastimateButton);
        gastimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                origin = (EditText)findViewById(R.id.editText);
                destination = (EditText)findViewById(R.id.editText2);
                originString = origin.getText().toString();
                destinationString = destination.getText().toString();
                
            }
        });
    }

}
