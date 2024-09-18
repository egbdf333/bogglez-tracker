/**
 * File: ChangeItem.java
 * Revision History:
 * - 2024-06-29: Function and variable declarations
 * - 2024-07-02: System redesign remove storing records into RAM
 * - 2024-07-03: Added toString() method for printing
 * - 2024-07-06: writeChangeItem implementation
 * - 2024-07-08: readChangeItem implementation
 * - 2024-07-09: moved generateRandomChangeID to Scenario Manager and made empty constructor for changeItem
 * - 2024-07-15: created two getters for status and product name for use in scenario manager
 * - 2024-07-25: documentation changes
 * Purpose:
 * ChangeItem class represents a change item of a particular product release and is responsible for
 * managing the change requests of the change item. The class stores data such as changeID, priority
 * status, anticipated release date, and a list of change requests.
 */
package ca.boggleztracker.model;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.Arrays;

public class ChangeItem {
    //=============================
    // Constants and static fields
    //=============================
    public static final int MAX_DESCRIPTION = 30; // accessed in TextUI
    public static final int MAX_STATUS = 12;
    public static final long BYTES_SIZE_CHANGE_ITEM = 146; // accessed in scenario manager

    //=============================
    // Member fields
    //=============================
    private int changeID;
    private char[] productName;
    private char[] releaseID;
    private char[] changeDescription;
    private char priority;
    private char[] status;
    private LocalDate anticipatedReleaseDate;

    //=============================
    // Constructors
    //=============================

    //-----------------------------
    /**
     * Default constructor for creating a ChangeItem.
     */
    //---
    public ChangeItem() {}

    //-----------------------------
    /**
     * Seven argument constructor for creating a "modified" ChangeItem.
     *
     * @param changeID (in) int - change ID of the change item.
     * @param productName (in) String - Product of change  is referencing to.
     * @param releaseID (in) String - Release ID change is referencing to
     * @param changeDescription (in) String - Description of change item.
     * @param priority (in) int - Priority of the change item (1 - 5)
     * @param status (in) String - status of the change item
     *               (Open, Assessed, In Progress, Completed, Cancelled).
     * @param anticipatedReleaseDate - Anticipated release date of the change item.
     */
    //---
    public ChangeItem(int changeID, String productName, String releaseID, String changeDescription,
                      char priority, String status, LocalDate anticipatedReleaseDate) {
        this.changeID = changeID;
        this.productName = ScenarioManager.padCharArray(productName.toCharArray(), Product.MAX_PRODUCT_NAME);
        this.releaseID = ScenarioManager.padCharArray(releaseID.toCharArray(), Release.MAX_RELEASE_ID);
        this.changeDescription = ScenarioManager.padCharArray(changeDescription.toCharArray(), MAX_DESCRIPTION);
        this.priority = priority;
        this.status = ScenarioManager.padCharArray(status.toCharArray(), MAX_STATUS);
        this.anticipatedReleaseDate = anticipatedReleaseDate;
    }

    //=============================
    // Methods
    //=============================

    //-----------------------------
    /**
     * returns the changeID of the object that it calls from.
     * @return (out) int - change ID of the object.
     */
    //---
    public int getChangeID(){
        return changeID;
    }

    //-----------------------------

    //-----------------------------
    /**
     * returns the status of the object that it calls from.
     * @return (out) char[] - status of the object
     */
    //---
    public char[] getStatus() {return status;}

    //-----------------------------
    /**
     * returns the product name of the object that it calls from.
     * @return (out) char[] - product name of the object
     */
    //---
    public char[] getProductName() {return productName;}

    //-----------------------------
    /**
     * returns the release id of the object that it calls from.
     * @return (out) char[] - release id of the object.
     */
    //---
    public char[] getReleaseID() {
        return releaseID;
    }

    //-----------------------------
    /**
     * returns the change description of the object that it calls from.
     * @return (out) char[] - description of the object.
     */
    //---
    public char[] getChangeDescription() {
        return changeDescription;
    }

    //-----------------------------
    /**
     * returns the priority of the object that it calls from.
     * @return (out) char - priority of the object.
     */
    //---
    public char getPriority() {
        return priority;
    }

    //-----------------------------
    /**
     * returns the date of the object that it calls from.
     * @return (out) String - date of the object.
     */
    //---
    public String getAnticipatedReleaseDate() {
        if (anticipatedReleaseDate == null) {
            return "";
        }
        return anticipatedReleaseDate.toString();
    }

    //-----------------------------
    /**
     * Writes the contents of release object to the release file.
     *
     * @param file (in) RandomAccessFile - The file to read from.
     */
    //---
    public void writeChangeItem(RandomAccessFile file) throws IOException {
        file.writeInt(changeID);
        file.writeChars(new String(productName));
        file.writeChars(new String(releaseID));
        file.writeChars(new String(changeDescription));
        file.writeChar(priority);
        file.writeChars(new String(status));

        if (anticipatedReleaseDate == null) {
            file.writeChars(" ".repeat(10));
        } else {
            file.writeChars(anticipatedReleaseDate.toString()); // format to yyyy-mm-dd (20 bytes)
        }
    }

    //-----------------------------
    /**
     * Reads individual change item record from file
     *
     * @param file (in) RandomAccessFile - The file to read from.
     */
    //---
    public void readChangeItems(RandomAccessFile file) throws IOException{
        changeID = file.readInt();
        productName = ScenarioManager.readCharsFromFile(file, Product.MAX_PRODUCT_NAME);
        releaseID = ScenarioManager.readCharsFromFile(file, Release.MAX_RELEASE_ID);
        changeDescription = ScenarioManager.readCharsFromFile(file, MAX_DESCRIPTION);
        priority = file.readChar();
        status = ScenarioManager.readCharsFromFile(file, MAX_STATUS);
        anticipatedReleaseDate = ScenarioManager.readDateFromFile(file);
    }
    //---

    /**
     * Utility method to print out contents of change request
     *
     * @return (out) String - change request object
     */
    @Override
    public String toString() {
        return "ChangeItem{" +
                "changeID=" + changeID +
                ", productName=" + Arrays.toString(productName) +
                ", releaseID=" + Arrays.toString(releaseID) +
                ", changeDescription=" + Arrays.toString(changeDescription) +
                ", priority=" + priority +
                ", status=" + Arrays.toString(status) +
                ", anticipatedReleaseDate=" + anticipatedReleaseDate +
                '}';
    }
}
