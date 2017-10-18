package com.example.magician.earthquake;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by magic on 10/11/2017.
 * this App use: Json / HttpURLConnection / InputStream & StreamFilter (InputStreamReader & BufferReader)
 * / AsynTaskLoader / ArrayAdapter /ListView (Customize a few Class and res)
 */
public class EarthquakeActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Item>>, SharedPreferences.OnSharedPreferenceChangeListener {
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private ArrayAdapter<Item> Adapter;
    private ListView earthquakeListView;
    private TextView emptyView;
    private ProgressBar progressBar;
    private LoaderManager loaderManager;
    private Button retryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);

        //loading spinner
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        // Retry Button
        retryButton = (Button) findViewById(R.id.retry_button);
        // Empty View
        emptyView = (TextView) findViewById(R.id.empty_view);

        // Obtain a reference to the SharedPreferences file for this app
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // And register to be notified of preference changes
        // So we know when the user has adjusted the query settings
        prefs.registerOnSharedPreferenceChangeListener(this);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        loaderManager = getLoaderManager();
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface). //can use .forceLoad();
        Log.i(LOG_TAG, "initLoader");
        //order Call:#0 , ##1 ,
        if (isNetworkConnectionAvailable(EarthquakeActivity.this)) {
            //hide Button
            retryButton.setVisibility(View.GONE);
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Hide loading indicator because the data has been loaded
            progressBar.setVisibility(View.GONE);
            // Set empty state text to display "No earthquakes found."
            emptyView.setText(R.string.no_internet);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(getString(R.string.settings_min_magnitude_key)) || key.equals(getString(R.string.settings_order_by_key))) {
            // Clear the ListView as a new query will be kicked off
            Adapter.clear();

            // Hide the empty state text view as the loading indicator will be displayed
            emptyView.setVisibility(View.GONE);

            // Show the loading indicator while new data is being fetched
            progressBar.setVisibility(View.VISIBLE);

            // Restart the loader to requery the USGS as the query settings have been updated
            getLoaderManager().restartLoader(EARTHQUAKE_LOADER_ID, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Log.i(LOG_TAG, "onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(LOG_TAG, "onOptionsItemSelected");
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //method to determine whether the device has internet connection
    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //can't use static here
    //this method can be used with AsyncTask and AsyncTaskLoader
    public void attachAdapterToView(final List<Item> earthquakes) {
        // Create a new {@link ArrayAdapter} of earthquakes
        if (Adapter == null) {
            Adapter = new ItemAdapter(this, earthquakes);
        } else {
            Adapter.addAll(earthquakes);
        }
        //Run adapter
        earthquakeListView.setAdapter(Adapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = earthquakes.get(i);
                String mUrl = item.getUrl();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);//or new Intent(Intent.ACTION_VIEW,Uri.parse(URL))
                browserIntent.setData(Uri.parse(mUrl));
                if (browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(browserIntent);
                }
            }
        });

    }

    //convert string to uri and then manipulate uri to take paramters from preferences of user
    public String formatUri() {
        // Obtain a reference to the SharedPreferences file for this app
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String maxMagnitude = sharedPrefs.getString(
                getString(R.string.settings_max_magnitude_key),
                getString(R.string.settings_max_magnitude_default));

        String itemNumber = sharedPrefs.getString(
                getString(R.string.settings_item_number_key),
                getString(R.string.settings_item_number_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        //return uri object from String
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        //return uriBuilder object to do the manipulate
        Uri.Builder uriBuilder = baseUri.buildUpon();
        //format uri
        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", itemNumber);
        uriBuilder.appendQueryParameter("minmag", minMagnitude);//here take value from Preferences
        uriBuilder.appendQueryParameter("maxmag", maxMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        //Log.v(LOG_TAG, "formatUri to: " + uriBuilder.toString());
        return uriBuilder.toString();
    }

    //order Call:#1, ,
    // Create a new loader for the given URI(formatUri())
    @Override
    public Loader<List<Item>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader ");
        return new EarthquakeLoader(EarthquakeActivity.this, formatUri());
    }

    // Hide text and retry button because the data has been loaded
    public void hide() {
        emptyView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);

    }

    //order Call:#5, ##2 ,
    @Override
    public void onLoadFinished(Loader<List<Item>> loader, List<Item> items) {
        // Hide loading indicator
        progressBar.setVisibility(View.GONE);

        //progressBarHorizontal.setVisibility(View.GONE);

        Log.i(LOG_TAG, "onLoadFinished ");
        // Clear the adapter of previous earthquake data
        if (Adapter != null && isNetworkConnectionAvailable(EarthquakeActivity.this)) {//cause wrong ui
            Adapter.clear();
        }
        //this method do UpdateUI,Call ListView.setAdapter
        // and Passing new ArrayList to Adapter object
        // If there is no result, do nothing.
        if (items != null && !items.isEmpty()) {
            attachAdapterToView(items);
        } else {
            // Set empty state text to display "No earthquakes found."
            emptyView.setText(R.string.no_earthquakes);
            retryButton.setText(R.string.retry_text);
            Log.i(LOG_TAG, "setEmptyView ");
        }

    }

    //only,Called in onDestroy()method before app close
    @Override
    public void onLoaderReset(Loader<List<Item>> loader) {
        Log.i(LOG_TAG, "onLoaderReset ");
        Adapter.clear(); // or attachAdapterToView(new ArrayList<Item>());
    }


    public void networkRetry(View view) {
        if (isNetworkConnectionAvailable(EarthquakeActivity.this)) {
            hide();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            Toast.makeText(EarthquakeActivity.this, "Still No Connection.", Toast.LENGTH_SHORT).show();
        }
    }

}

