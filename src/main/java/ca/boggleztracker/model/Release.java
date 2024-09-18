/**
 * File: Release.java
 * Revision History:
 * - 2024-06-29: Function and variable declarations
 * - 2024-07-02: System redesign remove storing records into RAM
 * - 2024-07-06: writeRelease implementation
 * - 2024-07-08: readRelease implementation
 * - 2024-07-25: documentation changes
 * Purpose:
 * Release class represents a release of a product in the system and is responsible for
 * managing the change items of the release. The class stores data such as release ID,
 * release date, and a list of change items.
 */
package ca.boggleztracker.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.Arrays;

public class Release {
    //=============================
    // Constants and static fields
    //=============================
    public static final int MAX_RELEASE_ID = 8; // used to limit user input length in TextUI
    public static final long BYTES_SIZE_RELEASE = 56; // used to calculate position in scenarioManager

    //=============================
    // Member fields
    //=============================
    private char[] productName;
    private char[] releaseID;
    private LocalDate date;

    //=============================
    // Constructors
    //=============================

    //-----------------------------
    /**
     * Default constructor for release
     */
    public Release() {
    }
    //---

    //-----------------------------
    /**
     * Two argument constructor for Release.
     *
     * @param productName (in) String - Product name of release version.
     * @param releaseID (in) String - ID of the release version.
     * @param date (in) LocalDate - Date of the release.
     */
    //---
    public Release(String productName, String releaseID, LocalDate date) {
        this.productName = ScenarioManager.padCharArray(productName.toCharArray(), Product.MAX_PRODUCT_NAME);
        this.releaseID = ScenarioManager.padCharArray(releaseID.toCharArray(), MAX_RELEASE_ID);
        this.date = date;
    }

    //=============================
    // Methods
    //=============================

    //-----------------------------

    /**
     * Getter method for product name.
     * @return (out) char[]
     */
    public char[] getProductName() {
        return productName;
    }

    //-----------------------------
    /**
     * returns the ReleaseID of the object that it calls from.
     * @return (out) char[] - the associated release ID.
     */
    //---
    public char[] getReleaseID() {
        return releaseID;
    }

    //-----------------------------
    /**
     * Writes the contents of release object to the release file.
     *
     * @param file (in) RandomAccessFile - The file to read from.
     */
    //---
    public void writeRelease(RandomAccessFile file) throws IOException {
        file.writeChars(new String(productName));
        file.writeChars(new String(releaseID));
        file.writeChars(date.toString()); // format to yyyy-mm-dd (20 bytes)
    }

    //-----------------------------
    /**
     * Checks file to see if an exact permutation of the three ProductRelease parameters already exists.
     *
     * @param file (in) RandomAccessFile - The file to read from.
     * @param releaseID (in) String - ID of the release version.
     * @return (out) boolean - true if the release already exists
     */
    //---
    public static boolean releaseExists(RandomAccessFile file, String releaseID) throws IOException {
        Release release = new Release();
        boolean releaseExists = false;
        char[] temp = ScenarioManager.padCharArray(releaseID.toCharArray(), MAX_RELEASE_ID);
        file.seek(0);
        try {
            while (true) {
                release.readRelease(file);
                if (Arrays.equals(temp, release.getReleaseID()))  {
                    releaseExists = true;
                }
            }
        } catch (EOFException e) {
            return releaseExists;
        }
    }

    //-----------------------------
    /**
     * Reads individual release record from file
     *
     * @param file (in) RandomAccessFile - The file to read from.
     */
    //---
    public void readRelease(RandomAccessFile file) throws IOException{
        productName = ScenarioManager.readCharsFromFile(file, Product.MAX_PRODUCT_NAME);
        releaseID = ScenarioManager.readCharsFromFile(file, Release.MAX_RELEASE_ID);
        date = ScenarioManager.readDateFromFile(file);
    }

    /**
     * Utility method to print out contents of release
     *
     * @return (out) String - release object
     */
    @Override
    public String toString() {
        return "Release{" +
                "productName=" + Arrays.toString(productName) +
                ", releaseID=" + Arrays.toString(releaseID) +
                ", date=" + date +
                '}';
    }
}
