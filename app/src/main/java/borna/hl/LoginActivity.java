package borna.hl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class LoginActivity extends Activity {
    Button b1,b2;
    EditText ed1,ed2;
    PreferencesManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);
        b2 = (Button)findViewById(R.id.button2);

        session = new PreferencesManager(getApplicationContext());
        if (session.isLoggedIn()) {
            getTasks();
            startTasksActivity();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().equals("admin") &&
                        ed2.getText().toString().equals("admin")) {
                        session.createLoginSession("admin");
                        startTasksActivity();
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    private ArrayList<Task> getTasks() {
        //implement server call and return ArrayList
        return new ArrayList<>();
    }

    private void startTasksActivity() {
//       getTasks(); should implement REST call
//       this is an example array, it should be created in a factory method and/or filled in with REST data
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Oreškovićeva 6/H"));
        tasks.add(new Task("Varičakova 13"));
        tasks.add(new Task("Siget 18C"));
        tasks.add(new Task("Naserov trg"));


        //region This is how you send an array of custom object to next activity (should be serializable)
        Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
        intent.putExtra("points", tasks);
        startActivity(intent);
        //endregion
        finish();
    }
}