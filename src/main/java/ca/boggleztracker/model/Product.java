/**
 * File: Product.java
 * Revision History:
 * - 2024-06-29: Function and variable declarations
 * - 2024-07-02: System redesign remove storing records into RAM
 * - 2024-07-04: writeProduct implementation
 * - 2024-07-08: readProduct implementation
 * - 2024-07-25: documentation changes & static method moved above constructor
 * Purpose:
 * Product class represents a product in the system and is responsible for
 * managing the releases of the product. The class stores data such as product name
 * and a list of releases.
 */
package ca.boggleztracker.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Product {
    //=============================
    // Constants and static fields
    //=============================
    public static final int MAX_PRODUCT_NAME = 10; // used to limit input string length for TextUI
    public static final long BYTES_SIZE_PRODUCT = 20; // used to calculate seek position on file

    //=============================
    // Member fields
    //=============================
    private char[] productName;

    //-----------------------------
    /**
     * Checks file to see if email already exists.
     *
     * @param file (in) RandomAccessFile - The file to read from.
     * @param productName (in) String - The product name is checked.
     * @return (out) boolean - Whether the product exists or not.
     */
    //---
    public static boolean productExists(RandomAccessFile file, String productName) throws IOException {
        Product product = new Product();
        boolean productExists = false;
        char[] temp = ScenarioManager.padCharArray(productName.toCharArray(), MAX_PRODUCT_NAME);
        file.seek(0);
        try {
            while (true) {
                product.readProduct(file);
                if (Arrays.equals(temp, product.getProductName())) {
                    productExists = true;
                }
            }
        } catch (EOFException e) {
            return productExists;
        }
    }

    //=============================
    // Constructors
    //=============================

    //-----------------------------
    /**
     * Default constructor for Product.
     */
    //---
    public Product() {
    }

    //-----------------------------
    /**
     * One argument constructor for Product.
     *
     * @param productName (in) String - Name of the product.
     */
    //---
    public Product(String productName) {
        this.productName = ScenarioManager.padCharArray(productName.toCharArray(), MAX_PRODUCT_NAME);
    }

    //=============================
    // Methods
    //=============================

    //-----------------------------

    /**
     * Getter method for product name character array.
     *
     * @return (out) char[] - product name char array
     */
    //---
    public char[] getProductName() {
        return productName;
    }

    //-----------------------------
    /**
     * Writes the contents of release object to the release file.
     *
     * @param file (in) RandomAccessFile - The file to read from.
     */
    //---
    public void writeProduct(RandomAccessFile file) throws IOException {
        file.writeChars(new String(productName));
    }

    //-----------------------------
    /**
     * Reads individual product record from file
     *
     * @param file (in) RandomAccessFile - The file to read from.
     */
    //---
    public void readProduct(RandomAccessFile file) throws IOException{
        productName = ScenarioManager.readCharsFromFile(file, Product.MAX_PRODUCT_NAME);
    }

    /**
     * Utility method to print out contents of product
     *
     * @return (out) String - product object
     */
    @Override
    public String toString() {
        return "Product{" +
                "productName=" + Arrays.toString(productName) +
                '}';
    }
}
