package com.example.android.quakereport;

/**
 * Created by liway on 24/08/2017.
 * represent information related to an earthquake that occurred
 */

public class Earthquake {

    // magnitude of the earthquake
    private double mMag;

    // city where earthquake occurred
    private String mCity;

    // date earthquake occurred
    private Long mDate;

    /**
     * Creates a new EarthquakeInfo object
     * @param mag is the magnitude of the earthquake
     * @param city is the location where the equake occurred
     * @param date is the date when the quake occurred
     */
    public Earthquake(double mag, String city, Long date){
        mMag = mag;
        mCity = city;
        mDate = date;

    }

    public double getMag() {
        return mMag;
    }

    public String getCity() {
        return mCity;
    }

    public Long getDate() {
        return mDate;
    }
}
