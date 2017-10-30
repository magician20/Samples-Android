package com.example.magician.petshelter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.magician.petshelter.data.PetContract.PetEntry;

/**
 * Created by magic on 10/20/2017.
 * PetDbHelper
 */

public class PetDbHelper extends SQLiteOpenHelper {
    /** Tag for the log messages */
    private static final String LOG_TAG = PetDbHelper.class.getSimpleName();
    //must define name and version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shelter.db";


    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //execute if not there's DB created before ,else will not execute
    @Override
    public void onCreate(SQLiteDatabase db) {
        // this is not the perfect data Types
        String CREATE_PET_TABLE = "CREATE TABLE " + PetEntry.TABLE_NAME + " ("
                + PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetEntry.COLUMN_PET_BREED + " TEXT, "
                + PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(CREATE_PET_TABLE);
        Log.v(LOG_TAG, CREATE_PET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
