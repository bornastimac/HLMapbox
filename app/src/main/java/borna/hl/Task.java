package borna.hl;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;

/**
 * Created by unknown_device on 2.7.2017..
 */

public class Task {
    Task(String address){
        this.address = address;
    }
    LatLng location;
    String address;
    ArrayList<ToDo> ToDos;
}

