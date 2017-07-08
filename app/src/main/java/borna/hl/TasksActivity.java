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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener= new LocationListener() {
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
        }catch (SecurityException x)
        {
            ;;;
        }
        populateTasksList();
    }

    private void populateTasksList() {
        // Construct the data source

        //REST CALL AND SPINNER should come here
        // ako ima zadatke(lokacije) pokreni mapactivity, inace pokreni listu mogucih zadataka(lokacija) za odabir
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://jsonplaceholder.typicode.com/posts/1";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TasksActivity.this, response , Toast.LENGTH_SHORT).show();
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

        //napuniti iz shared preferences?
        Bundle extras = getIntent().getExtras();
        ArrayList<Task> tasks = (ArrayList<Task>)getIntent().getSerializableExtra("points");
        // Create the adapter to convert the array to views
        TasksAdapter adapter = new TasksAdapter(this, tasks);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvTasks);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(i);

            }
        });
    }


}
//
//{"data":{"translations":[{"translatedText":"Bonjour tout le monde"}]}}
//        becomes:
//
//class DataWrapper {
//    public Data data;
//
//    public static DataWrapper fromJson(String s) {
//        return new Gson().fromJson(s, DataWrapper.class);
//    }
//    public String toString() {
//        return new Gson().toJson(this);
//    }
//}
//class Data {
//    public List<Translation> translations;
//}
//class Translation {
//    public String translatedText;
//}