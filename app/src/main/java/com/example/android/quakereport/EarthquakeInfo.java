package com.example.android.quakereport;

/**
 * Created by liway on 24/08/2017.
 * represent information related to an earthquake that occurred
 */

public class EarthquakeInfo {

    // magnitude of the earthquake
    private double mMag;

    // city where earthquake occurred
    private String mCity;

    // date earthquake occurred
    private String mDate;

    /**
     * Creates a new EarthquakeInfo object
     * @param mag
     * @param city
     * @param date
     */
    public EarthquakeInfo(double mag, String city, String date){
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

    public String getDate() {
        return mDate;
    }
}
