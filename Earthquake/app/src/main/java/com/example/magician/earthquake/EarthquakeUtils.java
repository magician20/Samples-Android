package com.example.magician.earthquake;


import android.text.TextUtils;
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

import static com.example.magician.earthquake.EarthquakeActivity.LOG_TAG;


/**
 * Created by magic on 10/11/2017.
 * Earthquake Utils
 */

final class EarthquakeUtils {
    // private static ArrayList<HashMap<String, String>> earthquakes;
    private static ArrayList<Item> earthquakes = new ArrayList<>();

    // Fetch Earthquake from Network connection
    static ArrayList<Item> fetchEarthquakeOnline(String url) {
        ArrayList<Item> earthquakeList = null;
        // Making a request to url and getting response
        //create the URL For request server
        URL earthquakeURL = CreateURL(url);

        // Perform HTTP request to the URL and receive a JSON String response back
        String jsonString = makeHttpRequest(earthquakeURL);

        //extract information from JSON string
        try {
            earthquakeList = extractEarthquakes(jsonString);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results: " + e.getMessage());
        }

        return earthquakeList;
    }

//    //used only with Local  files
//    static ArrayList<Item> fetchEarthquakeRaw(Context context, int res) {
//        // convert json file in R to a string
//        String jsonStr = loadJSONFromRaw(context, res);
//        // Log.e(LOG_TAG, "(jsonStr) Text Data:\\n" + jsonStr);
//        try {
//            extractEarthquake(jsonStr); //
//        } catch (JSONException e) {
//            Log.e(LOG_TAG, "Json parsing error: " + e.getMessage());
//        }
//
//        return earthquakes;
//    }

    //@return URL
    private static URL CreateURL(String url) {
        URL Url = null;
        try {
            Url = new URL(url);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException: " + e.getMessage());
        }
        return Url;
    }

    //@return  String represent Json File This Method work if u want to get array of object at once
    private static String makeHttpRequest(URL earthquakeUrl) {
        // TO DO:  open and create connection (this will return inputstream)
        if (earthquakeUrl == null) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String jsonString = null;
        try {
            urlConnection = (HttpURLConnection) earthquakeUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);//millisecond 10sec
            urlConnection.setConnectTimeout(15000); //millisecond 15sec
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                // convert inputstream into String
                jsonString = EarthquakeUtils.convertStreamToString(inputStream);

            } else {
                Log.e(LOG_TAG, "URL Error " + urlConnection.getResponseCode());

            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "URLConnection notOpen OR inpustream error: " + e.getMessage());
        } finally {
            //close connection then
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            //close inputstream
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "InputStream can't be Closed: " + e.getMessage());
                }
            }
        }

        return jsonString;
    }

    /**
     * Query the USGS dataset and return a list of  Earthquake objects.
     *
     * @return String represent Json File This Method work if u want to get all objects at once
     * extract information from json String
     */
    private static ArrayList<Item> extractEarthquakes(String jsonStr) throws JSONException {
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }
        // Create a JSONObject from the JSON response string
        JSONObject root = new JSONObject(jsonStr);
        //Log.v(LOG_TAG, "root data:\n" + root.toString());

        // Extract the JSONArray associated with the key called "features",
        // which represents a list of features (or earthquakes).
        JSONArray featuresArray = root.getJSONArray("features");
        // Log.v(LOG_TAG, "features data:\n" + featuresArray.toString());

        String[] stringsplace;
        for (int i = 0; i < featuresArray.length(); i++) {
            JSONObject feature = featuresArray.getJSONObject(i);
            //   Log.v(LOG_TAG, "features " + i + " data:\n" + featuresArray.toString());

            JSONObject properties = feature.getJSONObject("properties");
            //    Log.v(LOG_TAG, "features " + i + " data:\n" + properties.toString());


            double mag = properties.getDouble("mag");
            String place = properties.getString("place");
            Long timeInMilliseconds = properties.getLong("time");
            String url = properties.getString("url");

            Item item = new Item(mag, place, timeInMilliseconds, url);
            earthquakes.add(item);
        }

        return earthquakes;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader;
        StringBuilder stringBuilder = new StringBuilder();
        if (is != null) {
            reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
            //close inputstream after close HTTPconnection
        }

        return stringBuilder.toString();
    }

    // Uses With Local json file load and convert to string
//    private static String loadJSONFromRaw(Context context, int res) {
//        //this code return stream represent the file
//        InputStream is = context.getResources().openRawResource(res);// getAssets().open("eq_test.json");
//        Writer writer = new StringWriter();
//        try {
//            int size = is.available();// stores  binary data so it will be big number
//            char[] buffer = new char[size];// stores char so it will not be the same size ,it should be less than
//            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            int n;
//            while ((n = reader.read(buffer)) != -1) {
//                writer.write(buffer, 0, n);
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "IOException error stream can't be opened: " + e.getMessage());
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    Log.e(LOG_TAG, "IOException error stream can't be closed: " + e.getMessage());
//                }
//            }
//        }
//        return writer.toString();
//    }


}