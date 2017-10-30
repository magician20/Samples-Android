package com.example.magician.petshelter.data;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

/**
 * Created by magic on 10/20/2017.
 * PetContract
 */

public final class PetContract {
    //Query Keywords
    public static final String SEMICOMA = ";";
    public static final String COMA = ",";

    private PetContract() {

    }

    // Content authority of  PetProvider
    static final String CONTENT_AUTHORITY = "com.example.magician.provider.pets";
    // create uri the base content provider
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // PATH_Table pets
    static final String PATH_PETS = "pets";
    //Path Table pets any id
    static final String PATH_PETS_ANY_ID = "pets/#";
    // pets code
    static final int PETS_CODE = 1;
    // pets any id code
    static final int PET_ID_CODE = 2;
    // content uri  >> pets table path
    public static final Uri PETS_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);
    //  content uri >> pets table for a row
    public static final Uri PETS_CONTENT_URI_ROW = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS_ANY_ID);


    /* Inner class that defines the table pets contents
    * Each entry in the table represents a single pet.*/
    public static class PetEntry implements BaseColumns {
        /**
         * The MIME type of the  for a list of pets.
         * vnd.android.cursor.dir/com.example.android.pet/pets
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        /**
         * The MIME type of the  for a single pet.
         * vnd.android.cursor.item/com.example.android.pet/pets
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;


        /**
         * Name of database table for pets
         */
        public final static String TABLE_NAME = "pets";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";

        //Possible values for gender.
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        //check gender valid
        public static boolean isValidGender(int gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE) {
                return true;
            }
            return false;
        }

        public static String getGenderValue(int i) {
            switch (i) {
                case GENDER_MALE:
                    return "Male";
                case GENDER_FEMALE:
                    return "Female";
            }
            return "Unkown";
        }


    }


}
