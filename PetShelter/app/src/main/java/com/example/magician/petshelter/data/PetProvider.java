package com.example.magician.petshelter.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import static com.example.magician.petshelter.data.PetContract.PET_ID_CODE;
import static com.example.magician.petshelter.data.PetContract.PETS_CODE;
import static com.example.magician.petshelter.data.PetContract.PetEntry;

/**
 * Created by magic on 10/24/2017.
 * {@link ContentProvider} for Pets app.
 * URI / UriMatcher/
 */

public class PetProvider extends ContentProvider {
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = PetProvider.class.getName();
    // DB helper object
    private PetDbHelper petDbHelper;
    private SQLiteDatabase dbRead;
    private SQLiteDatabase dbWrite;

    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // assign code for each type of URI in my App
    static {
        //all the table point to it with code 1
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS_CODE);
        //any a row with id table point to it with code 2
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS_ANY_ID, PET_ID_CODE);
    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        //  Create and initialize a PetDbHelper object to gain access to the pets database.
        // Make sure the variable is a global variable, so it can be referenced from
        // other ContentProvider methods.

        petDbHelper = new PetDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection,
     * selection arguments, and sort order.
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Gets the data repository in read mode
        dbRead = petDbHelper.getReadableDatabase();

        //create  cursor object
        Cursor cursor = null;
          /*
         * Choose the table to query and a sort order based on the code returned for the incoming
         * URI.
         */
        switch (sUriMatcher.match(uri)) {
            // If the incoming URI was for all of table data(pets)
            case PETS_CODE:
                // Read all pet data
                cursor = dbRead.query(
                        PetEntry.TABLE_NAME,
                        projection,
                        null,     //selection
                        null,    //selectionArgs
                        null,     //groupBy
                        null,     //having
                        sortOrder       //sortOrder
                );
                break;

            // If the incoming URI was for a single row in table (pets)
            case PET_ID_CODE:
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};//point to a new values
                // Read data
                cursor = dbRead.query(
                        PetEntry.TABLE_NAME,
                        projection,
                        selection,     //selection
                        selectionArgs,    //selectionArgs
                        null,     //groupBy
                        null,     //having
                        sortOrder      //sortOrder
                );
                break;

            default:
                // If the URI is not recognized, you should do some error handling here.
                Log.v(LOG_TAG, "URI is not recognized");
                throw new IllegalArgumentException("Can't query unkown URI" + uri);
        }

        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        //check if there's values first
        if (contentValues.size() == 0) {
            return null;
        }
        // Check that the name is not null
        saintyCheck(contentValues);
        switch (sUriMatcher.match(uri)) {
            // If the incoming URI was for all of table data(pets)
            case PETS_CODE:
                return insertPet(uri, contentValues);

            // If the incoming URI was for a single row in table (pets)
            default:
                // If the URI is not recognized, you should do some error handling here.
                Log.v(LOG_TAG, "URI is not recognized");
                throw new IllegalArgumentException("Can't query unkown URI" + uri);
        }
    }

    private Uri insertPet(@NonNull Uri uri, ContentValues contentValues) {
        // Gets the data repository in write mode
        // new Row _ID value
        long newRowID;
        //new Uri {@return newUri}
        Uri newUri = null;

        dbWrite = petDbHelper.getWritableDatabase();
        newRowID = dbWrite.insertOrThrow(PetEntry.TABLE_NAME, null, contentValues);
        Log.v("CatalogActivity", "NEW RowID " + newRowID);

        newUri = ContentUris.withAppendedId(uri, newRowID);
        return newUri;
    }

    private void saintyCheck(ContentValues contentValues) {

        if (contentValues.containsKey(PetEntry.COLUMN_PET_NAME)) {
            String name = contentValues.getAsString(PetEntry.COLUMN_PET_NAME);
            if (checkEmpty(name)) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }
        //can be empty
        if (contentValues.containsKey(PetEntry.COLUMN_PET_BREED)) {
            String breed = contentValues.getAsString(PetEntry.COLUMN_PET_BREED);
            if (breed == null) {
                throw new IllegalArgumentException("Pet requires a breed");
            }
        }
        if (contentValues.containsKey(PetEntry.COLUMN_PET_GENDER)) {
            Integer gender = contentValues.getAsInteger(PetEntry.COLUMN_PET_GENDER);
            if (gender == null && PetEntry.isValidGender(gender)) {
                throw new IllegalArgumentException("Pet requires a gender");
            }
        }
        if (contentValues.containsKey(PetEntry.COLUMN_PET_WEIGHT)) {
            Integer weight = contentValues.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
            if (weight == null || weight < 0) {
                throw new IllegalArgumentException("Pet requires a weight");
            }
        }

    }

    private boolean checkEmpty(String s) {//string must be trim
        return TextUtils.isEmpty(s);
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        //check if there's values first
        if (contentValues.size() == 0) {
            return 0;
        }
        // Gets the data repository in write mode
        dbWrite = petDbHelper.getWritableDatabase();
        //number of rows Updated
        int numRowsUpdate = 0;
        //sainty check
        saintyCheck(contentValues);

        switch (sUriMatcher.match(uri)) {
            // If the incoming URI was for all of table data(pets)
            case PETS_CODE:
                return dbWrite.update(PetEntry.TABLE_NAME, contentValues, selection, selectionArgs);

            // If the incoming URI was for a single row in table (pets)
            case PET_ID_CODE:
//                selection = PetEntry._ID + "=?";
//                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return dbWrite.update(PetEntry.TABLE_NAME, contentValues, selection, selectionArgs);

            default:
                // If the URI is not recognized, you should do some error handling here.
                Log.v(LOG_TAG, "URI is not recognized");
                throw new IllegalArgumentException("Can't query unkown URI" + uri);
        }

    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Gets the data repository in write mode
        dbWrite = petDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            // If the incoming URI was for all of table data(pets)
            case PETS_CODE:
                // return the number of rows affected by delete
                return dbWrite.delete(PetEntry.TABLE_NAME, null, null);

            // If the incoming URI was for a single row in table (pets)
            case PET_ID_CODE:
                // Delete a single row given by the ID in the URI
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                // return the number of rows affected by delete
                return dbWrite.delete(PetEntry.TABLE_NAME, selection, selectionArgs);

            default:
                // If the URI is not recognized, you should do some error handling here.
                Log.v(LOG_TAG, "URI is not recognized");
                throw new IllegalArgumentException("Can't query unkown URI" + uri);
        }

    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS_CODE:
                return PetEntry.CONTENT_LIST_TYPE;
            case PET_ID_CODE:
                return PetEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
