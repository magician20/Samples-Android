package com.example.magician.earthquake;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by magic on 9/26/2017.
 * Iteam Class here is Earthquake data
 */


public class Item {
    private String place;
    private String locationOffset;
    private String primaryLocation;
    private long timeInMilliseconds;
    private String Date;
    private String Time;
    private String Url;
    private double Mag;
    private static final String LOCATION_SEPARATOR = " of ";
    private static final String NEAR_THE = "Near the";

    public Item(double Mag, String locationOffset, String primaryLocation, Long timeInMilliseconds, String Url) {
        this.Mag = Mag;
        this.locationOffset = locationOffset;
        this.primaryLocation = primaryLocation;
        this.timeInMilliseconds = timeInMilliseconds;
        this.Url = Url;
        Time = timeToDisplay(timeInMilliseconds);
        Date = dateToDisplay(timeInMilliseconds);
    }

    public Item(double Mag, String place, Long timeInMilliseconds, String Url) {
        this.place = place;
        this.Mag = Mag;
        this.timeInMilliseconds = timeInMilliseconds;
        this.Url = Url;
        calculateTimeAndDate();
    }

    private void calculateTimeAndDate() {
        String[] stringsPlace;
        if (place.contains(LOCATION_SEPARATOR)) {
            stringsPlace = place.split(LOCATION_SEPARATOR);
            locationOffset = stringsPlace[0];
            primaryLocation = stringsPlace[1];

        } else {
            locationOffset = NEAR_THE;
            primaryLocation = place;
        }
        Time = timeToDisplay(timeInMilliseconds);
        Date = dateToDisplay(timeInMilliseconds);
    }

    // determine  date of Earthquake
    private String dateToDisplay(Long l) {
        //long l = Long.parseLong(item.getDate());//timeInMilliseconds
        l = Math.abs(l);
        java.util.Date dateObject = new Date(l);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        //Log.v(LOG_TAG, "Date: ");
        return dateToDisplay;
    }

    // determine  time of Earthquake
    private String timeToDisplay(Long l) {
        //long l = Long.parseLong(item.getDate());//timeInMilliseconds
        l = Math.abs(l);
        Date dateObject = new Date(l);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a");
        String dateToDisplay = dateFormatter.format(dateObject);
        //Log.v(LOG_TAG, "Date: ");
        return dateToDisplay;
    }

//    public Item(double Mag, String locationOffset, String primaryLocation, String Date, String Time) {
//        this.Mag = Mag;
//        this.locationOffset = locationOffset;
//        this.primaryLocation = primaryLocation;
//        this.Date = Date;
//        this.Time = Time;
//
//    }

    public String getUrl() {
        return Url;
    }

    public String getLocationOffset() {
        return locationOffset;
    }

    public String getPrimaryLocation() {
        return primaryLocation;
    }

    public String getDate() {
        return Date;//or can add functions call to dateToDisplay and not safe valuse
    }

    public String getTime() {
        return Time;//or can add functions call to timeToDisplay
    }

    public double getMag() {
        return Mag;
    }


    /**
     * Returns the string representation of the {@link Item} object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Item{");
        sb.append(", Mag='" + Mag + '\'');
        sb.append(" , primaryLocation='" + primaryLocation + '\'');
        sb.append(" , timeInMilliseconds=" + timeInMilliseconds);
        return sb.toString();
    }
}
