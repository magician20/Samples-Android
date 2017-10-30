package com.example.magician.petshelter.data;

/**
 * Created by magic on 10/22/2017.
 * Gender Not used in code
 */

public enum Gender {
            GENDER_UNKNOWN("Uknown"),GENDER_MALE("Male"),GENDER_FEMALE("Female");
            private String value;

            private Gender(String value) {
                this.value=value;
            }

    @Override
    public String toString() {
        return this.value;
    }
}
