package borna.hl;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TasksActivity extends Activity {
    ArrayList<Task> tasks = new ArrayList<>();
    TasksAdapter adapter;


    @Override
    protected void onStart(){
        super.onStart();
        // Create the adapter to convert the array to views
        // Attach the adapter to a ListView
        ListView listView =  (ListView) findViewById(R.id.lvTasks);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tasks);
        //region This is how you recieve an array of custom object (which should be serializable)
        Bundle extras = getIntent().getExtras();
        tasks = (ArrayList<Task>)getIntent().getSerializableExtra("points");
        adapter = new TasksAdapter(this, tasks);
        //endregion
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Double latitude;
                Double longitude;
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, locationListener);
        } catch (SecurityException x) {
            Toast.makeText(this,"Omogućite lokacije na uređaju (Postavke/Lokacija)",Toast.LENGTH_LONG );
//            finishAffinity();
        }
        populateTasksList();
    }

    private void populateTasksList() {
        // Construct the data source
        //REST CALL AND SPINNER should come here
        // If there are tasks after server call run mapactivity, else run list activity to choose a task
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://jsonplaceholder.typicode.com/posts/1";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TasksActivity.this, "Sample REST API call  " + response , Toast.LENGTH_SHORT).show();
                                            //prebaciti string u object
                        try {
                            JSONObject json = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TasksActivity.this, "Server error" , Toast.LENGTH_SHORT).show();
            }
        });
        //send request
        queue.add(stringRequest);

        //REST CALL AND SPINNER END



    }


}
