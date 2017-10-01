package edu.sodetzpurdue.gastimator_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GastimatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastimator);
        Intent intent = getIntent();
        Trip trip = (Trip)intent.getSerializableExtra("trip");

    }
}
