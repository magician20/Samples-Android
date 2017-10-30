package com.example.magician.petshelter.data;

/**
 * Created by magic on 10/22/2017.
 * Pet
 */

public class Pet {
    private String name;
    private String breed;
    private int gender;
    private int weight;

    public Pet(String name, String breed) {
        this.name = name;
        this.breed = breed;
    }

    public Pet(String name, String breed, int gender, int weight) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getGender() {
        return gender;
    }

    public String getWeight() {
        return genderCheck(weight);
    }

    private String genderCheck(int gender) {
        switch (gender) {
            case 1:
                return Gender.GENDER_MALE.toString();
            case 2:
                return Gender.GENDER_FEMALE.toString();
        }
        return Gender.GENDER_UNKNOWN.toString();
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", gender=" + gender +
                ", weight=" + weight +
                '}';
    }
}
