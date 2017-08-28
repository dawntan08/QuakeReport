package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by liway on 24/08/2017.
 * custom adapter to cater to the EarthquakeInfo object
 */

public class EarthquakeInfoAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeInfoAdapter(Activity context, ArrayList<Earthquake> earthquakeInfo){
        super(context,0, earthquakeInfo);
    }

    // control how the listview items are created
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list, parent, false);
        }

        Earthquake currentEquakeInfo = getItem(position);

        // Find the TextView in the xml layout with the ID magTextView, and set its value
        TextView magTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        magTextView.setText(Double.toString(currentEquakeInfo.getMag()));

        // Find the TextView in the xml layout with the ID cityTextView, and set its value
        TextView cityTextView = (TextView) listItemView.findViewById(R.id.location);
        cityTextView.setText(currentEquakeInfo.getCity());

        // create Date object from the Equake info
        Date dateObject = new Date(currentEquakeInfo.getDate());

        // Find the TextView in the xml layout with the ID dateTextView, and set its value
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(formatDate(dateObject));

        // Find the TextView in the xml layout with the ID timeTextView, and set its value
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(formatTime(dateObject));

        // Return the whole list item layout so that it can be shown in the ListView
        return listItemView;

    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
