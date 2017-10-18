package com.example.magician.miwokapp;

/**
 * Created by magic on 9/26/2017.
 * Iteam Class
 */


public class Item {

    private int NO_IMAGE_PROVIDED = -1;

    private String defaultTranslation;
    private String miwokTranslation;
    private int ImageID = NO_IMAGE_PROVIDED;
    private int SoundID = NO_IMAGE_PROVIDED;

    public Item(String miwokTranslation, String defaultTranslation) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
    }

    // 3paremeters for pharase
    public Item(String miwokTranslation, String defaultTranslation, int SoundID) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.SoundID = SoundID;
    }

    // 4 paremters colors / familly / numbers
    public Item(String miwokTranslation, String defaultTranslation, int ImageID, int SoundID) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.ImageID = ImageID;
        this.SoundID = SoundID;
    }


    public void setDefaultTranslation(String defaultTranslation) {
        this.defaultTranslation = defaultTranslation;
    }

    public String getDefaultTranslation() {

        return defaultTranslation;
    }

    public void setMiwokTranslation(String miwokTranslation) {
        this.miwokTranslation = miwokTranslation;
    }

    public String getMiwokTranslation() {

        return miwokTranslation;
    }

    public void setImageID(int ImageID) {
        this.ImageID = ImageID;
    }

    public int getImageID() {

        return ImageID;
    }
    /*
    * @return whether or not there is an image for this word.
    * */
    public boolean hasImage() {

        return ImageID != NO_IMAGE_PROVIDED;
    }


    public void setSoundID(int SoundID) {
        this.SoundID = SoundID;
    }

    public int getSoundID() {

        return SoundID;
    }

    /*
    * @return whether or not there is an sound for this word.
    * */
    public boolean hasSound() {

        return SoundID != NO_IMAGE_PROVIDED;
    }

    /**
     * Returns the string representation of the {@link Item} object.
     */
    @Override
    public String toString() {
        return "Item{" +
                ", defaultTranslation='" + defaultTranslation + '\'' +
                ", miwokTranslation='" + miwokTranslation + '\'' +
                ", ImageID=" + ImageID +
                ", SoundID=" + SoundID +
                '}';
    }
}
