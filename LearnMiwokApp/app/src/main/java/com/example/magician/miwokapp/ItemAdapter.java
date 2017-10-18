package com.example.magician.miwokapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getColor;

/**
 * Created by magic on 9/26/2017.
 * Iteam Adapter
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    // id color
    private int IdColorActivity;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context         The current context. Used to inflate the layout file.
     * @param NumberListWords A List of NumberListWords objects to display in a list
     */
    public ItemAdapter(Context context, List<Item> NumberListWords) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, NumberListWords);
    }

    public ItemAdapter(Context context, List<Item> NumberListWords, int IdColorActivity) {

        super(context, 0, NumberListWords);
        this.IdColorActivity = IdColorActivity;
    }

    // View lookup cache & View holder ( holder Pattern)
    // here static usless
    private class ViewHolder {
        TextView MiwokTranslation;
        TextView DefaultTranslation;
        ImageView ImageUrl;
        ImageView PlayIcon;
        //ImageButton SoundPlay;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.word_custom_linear, parent, false);

            // get your views with the ViewHolder and attached to the inflated layout
           // viewHolder.SoundPlay = (ImageButton) convertView.findViewById(R.id.sound_button);
            viewHolder.ImageUrl = (ImageView) convertView.findViewById(R.id.image_view);
            viewHolder.MiwokTranslation = (TextView) convertView.findViewById(R.id.m_translation);
            viewHolder.DefaultTranslation = (TextView) convertView.findViewById(R.id.d_translation);
            viewHolder.PlayIcon = (ImageView)convertView.findViewById(R.id.play_icon);



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
            // Populate the data from the data object via the viewHolder object
            // into the template view.
            if (item.hasImage()) {
                //show the image if there's one
                viewHolder.ImageUrl.setImageResource(item.getImageID());
                viewHolder.ImageUrl.setVisibility(View.VISIBLE);
            } else {
                //hide the image if no there one
                viewHolder.ImageUrl.setVisibility(View.GONE);
            }

            viewHolder.MiwokTranslation.setText(item.getMiwokTranslation());
            viewHolder.DefaultTranslation.setText(item.getDefaultTranslation());
        }

         /*
         assign different background color for each activity use the list view
         by use Constructor take  int @parm of R.id.---- and then use convertView
         to findViewById << the container for these texts
         */
        //set the theme color for the list view
        View containerText = (View) convertView.findViewById(R.id.text_container);
        //find the color that the resource ID maps to
        //call ContextCompat getColor() to convert the color resource ID into an actual integer color value,
        // and return the result as the return value of the getMagnitudeColor() helper method.
        int color = ContextCompat.getColor(getContext(), IdColorActivity);
        containerText.setBackgroundColor(color);
        viewHolder.PlayIcon.setBackgroundColor(color);

        // Return the completed view to render on screen
        return convertView;
    }


}