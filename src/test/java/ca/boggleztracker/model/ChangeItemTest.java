/**
 * File: ChangeItemTest.java
 * Revision History:
 * - 2024-07-03: Test methods written
 * Purpose:
 * ChangeItemTest class is a unit test written to test all public methods of
 * ChangeItem.java.
 */

package ca.boggleztracker.model;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.io.RandomAccessFile;

class ChangeItemTest {
    //=============================
    // Member fields
    //=============================
    public int changeID = 0;
    public String releaseID = "v1.0";
    public String productName = "mySoftware";
    public String changeDescription = "Menu not populating";
    public char priority = '5';
    public String status = "Open";
    public LocalDate anticipatedReleaseDate = LocalDate.parse("2020-01-08");
    public String filename = "UnitTest01Text.dat";

    //=============================
    // Tests
    //=============================

    //-----------------------------
    /*
    *  Description: unit test to test the creation of the change item (constructor).
    *  Precondition: UnitTest01Text.dat contains no records.
    */
    void createANewChangeItem() {
        ChangeItem changeItem = new ChangeItem(changeID, releaseID, productName, changeDescription,
                priority, status, anticipatedReleaseDate);
        System.out.println(changeItem.toString());
    }

    //-----------------------------
    /*
    *   Description: Unit test to test the writing of a change item to a random access file.
    *   Precondition: UnitTest01Text.dat contains no records.
    */
    void testFileWriting() {
        ChangeItem changeItem = new ChangeItem(changeID,releaseID, productName, changeDescription,
                priority, status, anticipatedReleaseDate);
        try {
            RandomAccessFile myFile = new RandomAccessFile(filename, "rw");
            changeItem.writeChangeItem(myFile);
        }
        catch(IOException e) {
            System.out.println("File not found\n");
        }
    }

    //-----------------------------
    /*
    *   Description: Unit test to test the reading of a single change item from a random access file
    *   Precondition: UnitTest01Text.dat contains a single record with the variables initialized
    *                 earlier (can be done by running the previous test)
    */
    void testFileReading() {
        ChangeItem changeItem = new ChangeItem(changeID,releaseID, productName, changeDescription,
                priority, status, anticipatedReleaseDate);
        try {
            RandomAccessFile myFile = new RandomAccessFile(filename, "r");
            changeItem.readChangeItems(myFile);
            System.out.println(changeItem);
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("File not found\n");
        }
    }

    //-----------------------------
    /*
    *   Description: Unit test to test if the changeItemExists method reads and checks for change item
    *   Precondition: If UnitTest01Text.dat is empty return false. If UnitText01Text.dat contains
    *                 a single record with the variables initialized above, returns true.
    */
    /*
    void checkIfChangeItemExists() {
        ChangeItem changeItem = new ChangeItem(releaseID, productName, changeDescription,
                priority, status, anticipatedReleaseDate);
        try {
            RandomAccessFile myFile = new RandomAccessFile(filename, "r");
            changeItem.changeItemExists(myFile, priority, status, anticipatedReleaseDate);
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    */

    public static void main (String [] args) {
        ChangeItemTest test = new ChangeItemTest();
        test.createANewChangeItem();
        test.testFileWriting();
        test.testFileReading();
    }
}