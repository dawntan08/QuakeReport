package com.example.android.quakereport;

/**
 * Created by Liway on 8/24/2017.
 * helper class related to requesting and receiving info from USGS
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String LOG_TAG = "QueryUtils";
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Earthquake> extractFeatureFromJson(String stringUrl) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // create URL object
        URL url = createUrl(stringUrl);

        // perform HTTP request to above URL; receive JSON response
        String jsonResponse = "";
        try{
            jsonResponse = makeHttpRequest(url);
        } catch(IOException e){
            Log.e(LOG_TAG, "Error closing inputStream", e);
        }

        // parse jsonResponse, catch JSONexception to check proper JSON format in response
        try {
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.getJSONArray("features");
            // looping through all earthquakes
            for(int i =0; i < features.length(); i++){
                JSONObject c = features.getJSONObject(i);

                JSONObject properties = c.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String quakeUrl = properties.getString("url");
                earthquakes.add(new Earthquake(mag, place, time, quakeUrl));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }

    /**
     *  creates URL object from given String Url
     */
    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error creating URL ", e);
        }
            return url;
        }

    /**
     * makes an HTTP request to the provided URL and return a response String
     */
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        // check if URL is empty, return empty
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // check if response code to the connection is 200 (successful)
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else{
                Log.e(LOG_TAG, "Error response code from server: " +
                        urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem in retrieving the earthquake results.", e);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(urlConnection != null){
                inputStream.close();
            }
        }
        // need to close and disconnect the urlConnection and inputStream
        return jsonResponse;
    }

    /**
     * converts inputStream to String
     */
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

}