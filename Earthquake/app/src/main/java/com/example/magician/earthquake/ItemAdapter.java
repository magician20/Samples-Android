package com.example.magician.earthquake;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.List;



/**
 * Created by magic on 9/26/2017.
 * Iteam Adapter
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    // id color
    //private int magIdColor;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context  The current context. Used to inflate the layout file.
     * @param itemList A List of itemList objects to display in a list
     */
    public ItemAdapter(Context context, List<Item> itemList) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, itemList);
    }


    public ItemAdapter(Context context, List<Item> itemList, int magIdColor) {

        super(context, 0, itemList);
        //this.magIdColor = magIdColor;
    }


    // View lookup cache & View holder ( holder Pattern)
    // here static usless
    private class ViewHolder {
        TextView primaryLocation;
        TextView Date;
        TextView Mag;
        TextView Time;
        TextView locationOffset;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // create the ViewHolder variable that lookup cache stored in tag
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            // create a new ViewHolder
            viewHolder = new ViewHolder();

            //inflate() a static method, to return an instance of ItemView if we don’t have one to reuse
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout, parent, false);

            // get your views with the ViewHolder and attached to the inflated layout
            // viewHolder.magIcon = (ImageView) convertView.findViewById(R.id.image_view);
            viewHolder.Mag = (TextView) convertView.findViewById(R.id.text_mag);
            viewHolder.locationOffset = (TextView) convertView.findViewById(R.id.text_location_offset);
            viewHolder.primaryLocation = (TextView) convertView.findViewById(R.id.text_primary_location);
            viewHolder.Date = (TextView) convertView.findViewById(R.id.text_date);
            viewHolder.Time = (TextView) convertView.findViewById(R.id.text_time);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Get the data item for this position
        Item item = getItem(position);

        if (item != null) {
            //dateToDisplay(item); error here when i move list to top after down so not write code here

            viewHolder.Mag.setText(formatMag(item.getMag()));

            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable circleBackground = (GradientDrawable) viewHolder.Mag.getBackground();
            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(item.getMag());
            // Set the color on the magnitude circle
            circleBackground.setColor(magnitudeColor);

            viewHolder.primaryLocation.setText(item.getPrimaryLocation());
            viewHolder.locationOffset.setText(item.getLocationOffset());
            viewHolder.Date.setText(item.getDate());
            viewHolder.Time.setText(item.getTime());
        }

        //set the theme color for the list view
        //View containerText = (View) convertView.findViewById(R.id.custom_container);


        // Return the completed view to render on screen
        return convertView;
    }

    public String formatMag(double mag) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(mag);
    }

    public int getMagnitudeColor(double mag) {
        int magnitude = (int) Math.floor(mag);
        int magnitudeColor;
        switch (magnitude) {
            case 0:
            case 1:
                magnitudeColor = R.color.magnitude1;
                break;
            case 2:
                magnitudeColor = R.color.magnitude2;
                break;
            case 3:
                magnitudeColor = R.color.magnitude3;
                break;
            case 4:
                magnitudeColor = R.color.magnitude4;
                break;
            case 5:
                magnitudeColor = R.color.magnitude5;
                break;
            case 6:
                magnitudeColor = R.color.magnitude6;
                break;
            case 7:
                magnitudeColor = R.color.magnitude7;
                break;
            case 8:
                magnitudeColor = R.color.magnitude8;
                break;
            case 9:
                magnitudeColor = R.color.magnitude9;
                break;
            default:
                magnitudeColor = R.color.magnitude10plus;
                break;
        }
        //call ContextCompat getColor() to convert the color resource ID into an actual integer color value,
        // and return the result as the return value of the getMagnitudeColor() helper method.
        return ContextCompat.getColor(getContext(), magnitudeColor);
    }


}