package borna.hl;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
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
                        try {
                            JSONObject json = new JSONObject(response);
                            Toast.makeText(TasksActivity.this, response , Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //spremiti response u shared preferences?
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
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("Moslavacka 48"));
        tasks.add(new Task("Moslavacka 49"));
        tasks.add(new Task("Moslavacka 50"));
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