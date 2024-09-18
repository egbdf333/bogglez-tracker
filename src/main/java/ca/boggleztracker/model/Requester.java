/**
 * File: Requester.java
 * Revision History:
 * - 2024-06-29: Function and variable declarations
 * - 2024-07-02: System redesign remove storing records into RAM
 * - 2024-07-06: writeRequester implementation
 * - 2024-07-08: readRequester implementation
 * - 2024-07-10: requesterExists implementation
 * Purpose:
 * Requester class represents a requester in the system, storing data such as email,
 * name, phone number, and department.
 */
package ca.boggleztracker.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Requester {
    //=============================
    // Static fields
    //=============================
    public static final int MAX_EMAIL = 24;
    public static final int MAX_NAME = 30;
    public static final int MAX_DEPARTMENT = 2;
    public static final int PHONE_NUMBER_LENGTH = 11;
    public static final long BYTES_SIZE_REQUESTER = 120;

    //=============================
    // Member fields
    //=============================
    private char[] email;
    private char[] name;
    private long phoneNumber;
    private char[] department;

    //=============================
    // Constructors
    //=============================

    //-----------------------------
    /**
     * Default constructor for Requester
     */
    //---
    public Requester() {
    }

    //-----------------------------
    /**
     * Four argument constructor for Requester.
     *
     * @param email (in) String - Email address of the requester.
     * @param name (in) String - Name of the requester.
     * @param phoneNumber (in) String - Phone number of the requester.
     * @param department (in) String - Department of the requester. This can be left as an empty string.
     */
    //---
    public Requester(String email, String name, long phoneNumber, String department) {
        this.email = ScenarioManager.padCharArray(email.toCharArray(), MAX_EMAIL);
        this.name = ScenarioManager.padCharArray(name.toCharArray(), MAX_NAME);
        this.phoneNumber = phoneNumber;
        this.department = ScenarioManager.padCharArray(department.toCharArray(), MAX_DEPARTMENT);
    }

    //=============================
    // Methods
    //=============================

    //-----------------------------
    /**
     * Getter method for Requester email char array.
     *
     * @return (out) char[] - email char array of requester.
     */
    //---
    public char[] getEmail() {
        return email;
    }

    //-----------------------------

    //-----------------------------
    /**
     * Getter method for Requester name char array.
     *
     * @return (out) char[] - name char array of requester.
     */
    //---
    public char[] getName() {
        return name;
    }

    //-----------------------------
    /**
     * Formats phone number long type into a String.
     *
     * @return (out) String - formatted phone number.
     */
    //---
    public String formatPhoneNumber() {
        String phone = String.valueOf(phoneNumber);
        return phone.charAt(0) + "-" + phone.substring(1, 4) + "-" + phone.substring(4, 7) + "-" + phone.substring(7);
    }

    //-----------------------------

    /**
     * Writes the contents of Request object to the Request file.
     *
     * @param file (in) RandomAccessFile - The file to read from.
     */
    //---
    public void writeRequester(RandomAccessFile file) throws IOException {
        file.writeChars(new String(email));
        file.writeChars(new String(name));
        file.writeLong(phoneNumber);
        file.writeChars(new String(department));
    }

    //-----------------------------
    /**
     * Checks file to see if email already exists.
     *
     * @param file (in) RandomAccessFile - The file to read from.
     * @param email (in) String - The email to be checked.
     * @return (out) boolean - Whether the requester exists.
     */
    //---
    public static boolean requesterExists(RandomAccessFile file, String email) throws IOException {
        Requester requester = new Requester();
        boolean requesterExists = false;
        char[] temp = ScenarioManager.padCharArray(email.toCharArray(), MAX_EMAIL);
        file.seek(0);
        try {
            while (true) {
                requester.readRequester(file);
                if (Arrays.equals(temp, requester.getEmail())) {
                    requesterExists = true;
                }
            }
        } catch (EOFException e) {
            return requesterExists;
        }
    }

    //-----------------------------
    /**
     * Reads individual requester record from file
     *
     * @param file (in) RandomAccessFile - The file to read from.
     */
    //---
    public void readRequester(RandomAccessFile file) throws IOException{
        email = ScenarioManager.readCharsFromFile(file, Requester.MAX_EMAIL);
        name = ScenarioManager.readCharsFromFile(file, Requester.MAX_NAME);
        phoneNumber = file.readLong();
        department = ScenarioManager.readCharsFromFile(file, Requester.MAX_DEPARTMENT);
    }

    //-----------------------------
    /**
     * Utility method to print out contents of requester
     *
     * @return (out) String - requester object
     */
    //---
    @Override
    public String toString() {
        return "Requester{" +
                "email=" + Arrays.toString(email) +
                ", name=" + Arrays.toString(name) +
                ", phoneNumber=" + phoneNumber +
                ", department=" + Arrays.toString(department) +
                '}';
    }
}
