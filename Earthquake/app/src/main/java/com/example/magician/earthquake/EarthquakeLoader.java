package com.example.magician.earthquake;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by magic on 10/15/2017.
 * AsyncTaskLoader
 */

class EarthquakeLoader extends AsyncTaskLoader<List<Item>> {
    //Tag for log messages
    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String URL;
   // private Context mContext;

    //order Call:#2,   ,
    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.URL = url;
       // this.mContext = context;
        Log.i(LOG_TAG, "Call EarthquakeLoader Constructor ");
    }

    //order Call:#3 ,    ,###1
    // this method override from Loader Class , called automaticly by AsyncTaskLoader Constructor super
    // so u need create constructor with one argument(is context) at least.
    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "Call onStartLoading ");
        forceLoad();
    }

//    //Called on the main_menu thread to abort a load in progress.
//    @Override
//    public void cancelLoadInBackground() {
//        super.cancelLoadInBackground();
//    }
//
//    //Subclasses must implement this to take care of requests to forceLoad().
//    @Override
//    protected void onForceLoad() {
//        super.onForceLoad();
//    }
//
//    //Called if the task was canceled before it was completed.
//    @Override
//    public void onCanceled(List<Item> data) {
//        super.onCanceled(data);
//    }
//
//    // Subclasses must implement this to take care of requests to cancelLoad().
//    @Override
//    protected boolean onCancelLoad() {
//        return super.onCancelLoad();
//    }

    //order Call:#4,     ,###2
    @Override
    public List<Item> loadInBackground() {
        // this code to smulate slow connection
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (URL == null) {
            Log.e(LOG_TAG, "earthquakeList is null.");
            return null;
        }
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Item> earthquakeList = EarthquakeUtils.fetchEarthquakeOnline(URL);
        Log.i(LOG_TAG, "loadInBackground");
        return earthquakeList;
    }


}

