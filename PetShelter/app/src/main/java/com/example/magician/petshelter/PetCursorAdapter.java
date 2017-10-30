package com.example.magician.petshelter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.magician.petshelter.data.PetContract.PetEntry;

/**
 * Created by magic on 10/25/2017.
 * CursorAdapter
 */

public class PetCursorAdapter extends CursorAdapter {
    private static final String LOG_TAG = PetCursorAdapter.class.getName();

    PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        Log.v(LOG_TAG, "Adpter construtor finished");
    }

    //here newView used inside getView To avoid inflating ,But to avoiding many findViewById calls
    // you have to do it (and this can't consume too much time than inflate Method)
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.v(LOG_TAG, "newView Method");

        View inflateView = LayoutInflater.from(context).
                inflate(R.layout.catalog_custom_layout, parent, false);

        // create a new ViewHolder
        ViewHolder viewHolder = new ViewHolder(inflateView);
        Log.v(LOG_TAG, "Create ViewHolder");
        // Cache the viewHolder object inside the fresh view
        inflateView.setTag(viewHolder);

        return inflateView;
    }

    /**
     * Cache of the children views for a list item.
     */
    private class ViewHolder {
        TextView name;
        TextView breed;
        TextView gender;
        TextView weight;

        // Find fields to populate in inflated template
        public ViewHolder(View view) {
            this.name = (TextView) view.findViewById(R.id.text_pet_name);
            this.breed = (TextView) view.findViewById(R.id.text_pet_breed);
            this.gender = (TextView) view.findViewById(R.id.text_pet_gender);
            this.weight = (TextView) view.findViewById(R.id.text_pet_weight);
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // we've just avoided calling findViewById() on resource everytime
        // just use the viewHolder
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        //get values from cursor
        String name = cursor.getString(cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME));
        String breed = cursor.getString(cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED));
        int gender = cursor.getInt(cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER));
        int weight = cursor.getInt(cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT));

        // assign values to Views
        viewHolder.name.setText(name);
        viewHolder.breed.setText(breed);
        viewHolder.gender.setText(PetEntry.getGenderValue(gender));
        viewHolder.weight.setText(String.valueOf(weight));

        Log.v(LOG_TAG, "bindView Method done! ");

    }


}
