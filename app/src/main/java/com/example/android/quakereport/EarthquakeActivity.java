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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String USGS_QUERY_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private EarthquakeInfoAdapter mAdapter;
    ArrayList<Earthquake> earthquakes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // create adapter whose data is the earthquake data
        mAdapter = new EarthquakeInfoAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get Earthquake object in the list which was clicked
                Earthquake earthquake = earthquakes.get(position);

                //get url info from the Earthquake object and open it in a browser
                openWebPage(earthquake.getUrl());
            }
        });

        // Start the AsyncTask to fetch the earthquake data
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_QUERY_URL);
    }

    /**
     *  sends implicit intent to open the provided url in a browser
     *
     */
    public void openWebPage(String url) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{
        protected List<Earthquake> doInBackground(String... urls){
            // check if url is null, or if there are no urls
            if(urls.length< 1 || urls[0] == null){
                return null;
            }
            List<Earthquake> result = QueryUtils.extractFeatureFromJson(urls[0]);
            return result;
        }

        protected void onPostExecute(List<Earthquake> result){
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (result != null && !result.isEmpty()) {
                mAdapter.addAll(result);
            }
        }
    }
}
