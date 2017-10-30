package com.example.magician.petshelter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magician.petshelter.data.Pet;
import com.example.magician.petshelter.data.PetContract;
import com.example.magician.petshelter.data.PetContract.PetEntry;
import com.example.magician.petshelter.data.PetDbHelper;

import java.util.ArrayList;

import static android.R.attr.data;

public class CatalogActivity extends AppCompatActivity {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = CatalogActivity.class.getName();
    private PetDbHelper mDbHelper;
    private ListView listPets;
    private PetCursorAdapter adapter;
    private Cursor cursor;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        //Toolbar used to set ActionBar or not at runtime
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        listPets = findViewById(R.id.list_pets);
        //create and set empty view to list
        emptyView = findViewById(R.id.empty_view);
        listPets.setEmptyView(emptyView);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new PetDbHelper(this);
        Log.v(LOG_TAG, "Create DBHelper");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // displayDatabaseInfo();
        cursor = readAllBD();
        attachAdapterToView(cursor);
        Log.v(LOG_TAG, "onStart");
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        cursor.close();
        super.onDestroy();
        Log.v(LOG_TAG, "onDestroy");
    }

    //Display a List of Views
    private void attachAdapterToView(Cursor cursor) {
        if (adapter == null) {
            adapter = new PetCursorAdapter(CatalogActivity.this, cursor);
            Log.v(LOG_TAG, "adapter created.");
        } else {
            // Switch to new cursor and update contents of ListView also close the old one.
            adapter.changeCursor(cursor);
            Log.v(LOG_TAG, "adapter changed.");
        }

        //attach adapter to ListView
        listPets.setAdapter(adapter);
        Log.v(LOG_TAG, "adapter attached.");
//        listPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent editIntent = new Intent();
//                    startActivity(editIntent);
//
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertDummiePet();
                // displayDatabaseInfo();
                cursor = readAllBD();
                attachAdapterToView(cursor);
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Deleta Table Rows
                DeleteAllPet();
                //  displayDatabaseInfo();
                cursor = readAllBD();
                attachAdapterToView(cursor);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    // insert dummie data
    private void insertDummiePet() {
        //new URi
        Uri mNewUri;
        // this is the dummie data
        String name = "Toto";
        String breed = "Terrier";
        int gender = PetEntry.GENDER_MALE;
        int weight = 7;

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, name);
        values.put(PetEntry.COLUMN_PET_BREED, breed);
        values.put(PetEntry.COLUMN_PET_GENDER, gender);
        values.put(PetEntry.COLUMN_PET_WEIGHT, weight);

        mNewUri = getContentResolver().insert(PetContract.PETS_CONTENT_URI, values);

        long newRowId = ContentUris.parseId(mNewUri);

        if (newRowId != -1) {
            // handleInsertOutUri
            Toast.makeText(this, "Pet Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Pet Not Saved", Toast.LENGTH_SHORT).show();
        }
    }


    private Cursor readAllBD() {
        //Create Container for back result
        //projection columns that want to show (results)
        String[] projection = new String[]{
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT
        };
        // this call query in petProvider
        Cursor cursor = getContentResolver().query(PetContract.PETS_CONTENT_URI, projection, null, null, null);
        // get data from cursor
        // getCursorData(cursor, projection);

        return cursor;
    }

    private ArrayList<Pet> arrayListPets;

    // get data from cursor
    public void getCursorData(Cursor cursor, String[] projection) {
        arrayListPets = new ArrayList<Pet>();
        while (cursor.moveToNext()) { // get data from Cursor
            String name = cursor.getString(cursor.getColumnIndexOrThrow(projection[1]));
            String breed = cursor.getString(cursor.getColumnIndexOrThrow(projection[2]));
            int gender = cursor.getInt(cursor.getColumnIndexOrThrow(projection[3]));
            int weight = cursor.getInt(cursor.getColumnIndexOrThrow(projection[4]));
            arrayListPets.add(new Pet(name, breed, gender, weight));
        }
        Log.v(LOG_TAG, "cursor: " + arrayListPets.toString());
    }

    public void DeleteAllPet() {
        // Defines a variable to contain the number of rows deleted
        int numRowsDeleted;

        //define condition to delete data
        String selection = PetEntry._ID + "=?";
        String[] selectionArgs = {""};
        //this call query in petProvider
        numRowsDeleted = getContentResolver().
                delete(PetContract.PETS_CONTENT_URI, selection, selectionArgs);
        Log.v(LOG_TAG, "Number of deleted rows: " + numRowsDeleted);
        if (numRowsDeleted > 0) {
            Toast.makeText(this, R.string.delete_ِall_pet_successful, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.delete_pet_failed, Toast.LENGTH_SHORT).show();
        }
    }


}
