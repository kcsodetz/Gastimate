package edu.sodetzpurdue.gastimator_app;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class VehicleListActivity extends AppCompatActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = mPrefs.edit();
        final Gson gson = new Gson();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_car);
        this.setTitle("Choose or Add a Vehicle");
        lv = (ListView) findViewById(R.id.listView);
        final Intent intent = getIntent();
        if (intent.hasExtra("car")){
            Car car = (Car)intent.getSerializableExtra("car");
            System.out.println(car.getModel());
            String json = gson.toJson(car);
            prefsEditor.putString(car.getModel(), json);
            prefsEditor.apply();
        }
        final List<String> carList = new ArrayList<>();
        final Map<String,?> keys = mPrefs.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            carList.add(entry.getKey());
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_expandable_list_item_1,
                carList);

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg){
                Intent intent1 = new Intent(VehicleListActivity.this, DestinationActivity.class);
                String key = (String) adapter.getAdapter().getItem(position);
                String json = mPrefs.getString(key, "");
                Car car = gson.fromJson(json, Car.class);
                intent1.putExtra("car", car);
                startActivity(intent1);
            }
        }

        );

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {

                prefsEditor.remove(parent.getItemAtPosition(position).toString());
                prefsEditor.commit();
                carList.remove(removeElement(parent.getItemAtPosition(position).toString(), carList));

                arrayAdapter.notifyDataSetChanged();

                return true;
            }

        });
    }

    public static int removeElement(String element, List<String> items)
    {
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).equals(element))
                return i;
        }
        return -1;
    }

    public void pressedFAB(View view){
        Intent intent = new Intent(this, AddVehicleActivity.class);
        startActivity(intent);
    }


}
