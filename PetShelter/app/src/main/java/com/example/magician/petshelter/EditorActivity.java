package com.example.magician.petshelter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.magician.petshelter.data.PetContract;
import com.example.magician.petshelter.data.PetContract.PetEntry;
import com.example.magician.petshelter.data.PetDbHelper;


public class EditorActivity extends AppCompatActivity {
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = EditorActivity.class.getName();

    /**
     * EditText field to enter the pet's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mBreedEditText;

    /**
     * EditText field to enter the pet's weight
     */
    private EditText mWeightEditText;

    /**
     * EditText field to enter the pet's gender
     */
    private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;

    private PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new PetDbHelper(this);
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = PetEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Default: Unknown
            }
        });

    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //newRowId
        long newRowId = -1;
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertPet();
                finish();
//                    Intent intent = new Intent(EditorActivity.this, CatalogActivity.class);
//                    startActivity(intent);
                // Navigate back to parent activity (CatalogActivity)
//                    NavUtils.navigateUpFromSameTask(this);
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                deletPet(); // need id of pet we want to delete from UI
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @NonNull
    private void insertPet() {
        // Defines a new Uri object that receives the result of the insertion
        Uri mNewUri;
        //still need to check these values ,ex: insert numbers not names or not insert anything
        //trim() remove extra space
        String name = mNameEditText.getText().toString().trim();
        String breed = mBreedEditText.getText().toString().trim();
        int gender = (int) mGenderSpinner.getSelectedItemId();// return number long or use .getSelectedItemPosition

        int weight = (TextUtils.isEmpty(mWeightEditText.getText().toString().trim())) ?
                0 : Integer.parseInt(mWeightEditText.getText().toString().trim());
//        int weight = (checkEmpty(mWeightEditText.getText().toString().trim())) ?
//                0 : Integer.parseInt(mWeightEditText.getText().toString().trim());

        Log.v("Spinner value", mGenderSpinner.getSelectedItem().toString());
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, name);
        values.put(PetEntry.COLUMN_PET_BREED, breed);
        values.put(PetEntry.COLUMN_PET_GENDER, gender);
        values.put(PetEntry.COLUMN_PET_WEIGHT, weight);
        Log.v("mGenderSpinner", "" + gender);

        mNewUri = getContentResolver().insert(PetContract.PETS_CONTENT_URI, values);
        long newRowId = ContentUris.parseId(mNewUri);
        if (newRowId != -1) {
            // handleInsertOutUri
            Toast.makeText(this, R.string.insert_pet_successful, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.insert_pet_failed, Toast.LENGTH_SHORT).show();
        }
    }

    //need id of the pet to delet it FRom UI (then we can passe id with selection args or with contentUri)
    private void deletPet() {
        // Defines a variable to contain the number of rows deleted
        int numRowsDeleted;

        //define condition to delete data
        String selection = PetEntry._ID + "=?";
        String[] selectionArgs = {"2", "3", "4", "5"};
        //this call query in petProvider
        numRowsDeleted = getContentResolver().
                delete(PetContract.PETS_CONTENT_URI_ROW, selection, selectionArgs);
        Log.v(LOG_TAG, "Number of deleted rows: " + numRowsDeleted);

        if (numRowsDeleted > 0) {
            Toast.makeText(this, R.string.delete_pet_successful, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.delete_pet_failed, Toast.LENGTH_SHORT).show();
        }
    }


}
