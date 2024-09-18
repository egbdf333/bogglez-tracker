/**
 * File: Main.java
 * Revision History:
 * - 2024-07-04: File creation
 * - 2024-07-15: Added Unit test for reading, writing and modifying to file for ChangeItem and Requester
 * Purpose: TestFileOps class represents a unit test to test writing and reading of data records.
 */
package ca.boggleztracker.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;

public class TestFileOps {
    //=============================
    // Static Method Declarations
    //=============================

    //-----------------------------
    /*
     *   Description: Unit test to test the writing of a Requester to a random access file.
     *   Precondition: UnitTest02Text.dat contains no records.
     */
    static void testRequesterWrite(Requester testerRequester){
        try{
            RandomAccessFile myFile = new RandomAccessFile("UnitTest02Text.dat", "rw");
            myFile.seek(myFile.length());
            testerRequester.writeRequester(myFile);
            System.out.println("writeRequester: TEST PASSED");
        }catch(IOException e){
            System.out.println("writeRequester: TEST FAILED");
            System.err.println("Error opening record files " + e.getMessage());
        }
    }

    //-----------------------------
    /*
     *   Description: Unit test to test the reading of a Requester from a random access file
     *   Precondition: UnitTest02Text.dat contains a single record with the variables initialized
     *                 earlier (can be done by running the previous test)
     */
    static void testRequesterRead(Requester testerRequester){
        try{
            RandomAccessFile myFile = new RandomAccessFile("UnitTest02Text.dat", "rw");
            myFile.seek(0);
            testerRequester.readRequester(myFile);
            System.out.println("readRequester: TEST PASSED");
        }catch(IOException e){
            System.out.println("readRequester: TEST FAILED");
            System.err.println("Error opening record files " + e.getMessage());
        }
    }

    //-----------------------------
    /*
     *   Description: Unit test to test the writing of a change Item to a random access file.
     *   Precondition: UnitTest01Text.dat contains no records.
     */
    static void testChangeItemWrite(ChangeItem testerChangeItem){
        try{
            RandomAccessFile myFile = new RandomAccessFile("UnitTest01Text.dat", "rw");
            myFile.seek(myFile.length());
            testerChangeItem.writeChangeItem(myFile);
            System.out.println("writeChangeItem: TEST PASSED");
        }catch(IOException e){
            System.out.println("writeChangeItem: TEST FAILED");
            System.err.println("Error opening record files " + e.getMessage());
        }
    }

    //-----------------------------
    /*
     *   Description: Unit test to test the reading of a single change item from a random access file
     *   Precondition: UnitTest01Text.dat contains a single record with the variables initialized
     *                 earlier (can be done by running the previous test)
     */
    static void testChangeItemRead(ChangeItem testerChangeItem){
        try{
            RandomAccessFile myFile = new RandomAccessFile("UnitTest01Text.dat", "rw");
            myFile.seek(0);
            testerChangeItem.readChangeItems(myFile);
            System.out.println("readChangeItem: TEST PASSED");
        }catch(IOException e){
            System.out.println("readChangeItem: TEST FAILED");
            System.err.println("Error opening record files " + e.getMessage());
        }
    }

    //-----------------------------
    /*
     *   Description: Unit test to test the modifying of a single change item from a random access file
     *   Precondition: UnitTest01Text.dat contains a single record with the variables initialized
     *                 earlier (can be done by running the previous test)
     */
    static void testModifyChangeItem(ChangeItem ChangeItem){
        try{
            ScenarioManager testerManager = new ScenarioManager();
            RandomAccessFile myFile = new RandomAccessFile("UnitTest01Text.dat", "rw");
            ChangeItem testerModifiedChangeItem = new ChangeItem(0,"TestProd2","v1.1",
                    "Test Description 2",'4', "Accessed", LocalDate.of(2024,8,20));
            testerManager.modifyChangeItem(myFile,0, testerModifiedChangeItem);
            if(ChangeItem.getStatus() != testerModifiedChangeItem.getStatus()){
                System.out.println("modifyChangeItem: TEST PASSED");
            }else {
                System.out.println("modifyChangeItem: TEST FAILED");
            }
        }catch (IOException e){
            System.out.println("modifyChangeItem: TEST FAILED");
        }
    }

    //-----------------------------
    /**
     * Unit Test of Requester and ChangeItem reading, writing, and modifying to files
     * @param args (in) String[] - Command line arguments
     */
    //---
    public static void main(String[] args) {
        // TEST FOR REQUESTER
        System.out.println("Starting Requester unit test:");
        Requester Requester = new Requester("testEmail@sfu.ca","Test Name", 16046041111L,"QA");
        testRequesterWrite(Requester);
        testRequesterRead(Requester);

        // TEST FOR CHANGEITEM
        System.out.println("Starting ChangeItem unit test:");
        ChangeItem ChangeItem = new ChangeItem(0,"TestProd","v1.0", "Test Description",'5', "Open", LocalDate.of(2024,8,8));
        testChangeItemWrite(ChangeItem);
        testChangeItemRead(ChangeItem);
        testModifyChangeItem(ChangeItem);
    }

}
