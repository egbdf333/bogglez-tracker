/**
 * File: ChangeRequest.java
 * Revision History:
 * - 2024-06-29: Function and variable declarations
 * - 2024-07-02: System redesign remove storing records into RAM
 * - 2024-07-06: writeChangeRequest implementation
 * - 2024-07-08: readChangeRequest implementation
 * - 2024-07-25: documentation changes
 * Purpose:
 * ChangeRequest class represents a change request of a product, storing data such as
 * reported date and the requester.
 */
package ca.boggleztracker.model;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.Arrays;

public class ChangeRequest {
    //=============================
    // Constants and static fields
    //=============================
    public static final int BYTES_SIZE_CHANGE_REQUEST = 108; // accessed to calculate start position in file seeking

    //=============================
    // Member fields
    //=============================
    private int changeID;
    private char[] productName;
    private char[] reportedRelease;
    private char[] requesterEmail;
    private LocalDate reportedDate;

    //=============================
    // Constructors
    //=============================

    //-----------------------------
    /**
     * Default constructor for change request.
     */
    //---
    public ChangeRequest() {
    }

    //-----------------------------
    /**
     * Five argument constructor for change request.
     *
     * @param changeID (in) int - Identifier for the ChangeItem this request is for.
     * @param productName (in) String - Name of product of change request.
     * @param reportedRelease (in) String - Release version of the product
     * @param requesterEmail (in) String - Email of the requester.
     * @param reportedDate (in) LocalDate - Date of when the request was made.
     */
    //---
    public ChangeRequest(int changeID, String productName, String reportedRelease,
                         String requesterEmail, LocalDate reportedDate) {
        this.changeID = changeID;
        this.productName = ScenarioManager.padCharArray(productName.toCharArray(), Product.MAX_PRODUCT_NAME);
        this.reportedRelease = ScenarioManager.padCharArray(reportedRelease.toCharArray(), Release.MAX_RELEASE_ID);
        this.requesterEmail = ScenarioManager.padCharArray(requesterEmail.toCharArray(), Requester.MAX_EMAIL);
        this.reportedDate = reportedDate;
    }

    //=============================
    // Methods
    //=============================

    /**
     * Getter method to retrieve requester email.
     *
     * @return (out) char[] - character array of requester email.
     */
    public char[] getRequesterEmail() {
        return requesterEmail;
    }

    /**
     * Getter method to retrieve change ID.
     *
     * @return (out) int - change id.
     */
    public int getChangeID() {
        return changeID;
    }

    //-----------------------------
    /**
     * Writes the contents of release object to the release file.
     *
     * @param file (in) RandomAccessFile - The file to read from.
     */
    //---
    public void writeChangeRequest(RandomAccessFile file) throws IOException {
        file.writeInt(changeID);
        file.writeChars(new String(productName));
        file.writeChars(new String(reportedRelease));
        file.writeChars(new String(requesterEmail));
        file.writeChars(reportedDate.toString()); // format to yyyy-mm-dd (20 bytes)
    }

    //-----------------------------
    /**
     * Reads individual change request record from file
     *
     * @param file (in) RandomAccessFile - The file to read from.
     */
    //---
    public void readChangeRequest(RandomAccessFile file) throws IOException{
        changeID = file.readInt();
        productName = ScenarioManager.readCharsFromFile(file, Product.MAX_PRODUCT_NAME);
        reportedRelease = ScenarioManager.readCharsFromFile(file, Release.MAX_RELEASE_ID);
        requesterEmail = ScenarioManager.readCharsFromFile(file, Requester.MAX_EMAIL);
        reportedDate = ScenarioManager.readDateFromFile(file);
    }

    //-----------------------------
    /**
     * Utility method to print out contents of change request
     *
     * @return (out) String - change request object
     */
    //---
    @Override
    public String toString() {
        return "ChangeRequest{" +
                "changeID=" + changeID +
                ", productName=" + Arrays.toString(productName) +
                ", reportedRelease=" + Arrays.toString(reportedRelease) +
                ", requesterEmail=" + Arrays.toString(requesterEmail) +
                ", reportedDate=" + reportedDate +
                '}';
    }
}
