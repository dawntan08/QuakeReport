/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

//        // Create a fake list of earthquake locations with magnitude, date
//        ArrayList<Earthquake> earthquakes = new ArrayList<>();
//        earthquakes.add(new Earthquake(5.6, "San Francisco", "July 25, 2017"));
//        earthquakes.add(new Earthquake(3.5, "London", "May 25, 2017"));
//        earthquakes.add(new Earthquake(5.6, "Tokyo", "July 25, 2017"));
//        earthquakes.add(new Earthquake(9.6, "San Francisco", "July 25, 2017"));
//        earthquakes.add(new Earthquake(5.8, "Moscow", "July 25, 2017"));
//        earthquakes.add(new Earthquake(2.6, "Sio de ", "July 25, 2017"));
//        earthquakes.add(new Earthquake(3.6, "Paris", "July 25, 2017"));

        // Create a fake list of earthquakes.
        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // create adapter whose data is the earthquake data
        EarthquakeInfoAdapter adapter = new EarthquakeInfoAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}
