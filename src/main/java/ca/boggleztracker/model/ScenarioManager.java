/**
 * File: ScenarioManager.java
 * Revision History:
 * - 2024-06-29: Function and variable declarations
 * - 2024-07-02: System redesign remove storing records into RAM
 * - 2024-07-04: Created individual RandomAccessFiles for each file, opened on start and closed on system shut down.
 * - 2024-07-08: Completed add methods
 * - 2024-07-08: read helper methods
 * - 2024-07-09: implemented modify changeItem, modify release, and generateRandomChangeID
 * - 2024-07-10: implemented modified add requester to check if email already exists
 * - 2024-07-13: implemented all add methods with uniqueness check
 * - 2024-07-14: implemented generateRequesterPage method
 * - 2024-07-15: implemented all generate pages methods
 * - 2024-07-25: documentation changes
 * Purpose:
 * ScenarioManager class is responsible for opening and closing the data file,
 * populating the array lists of products and requesters, and supports various interactions
 * with these lists.
 */

package ca.boggleztracker.model;


import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ScenarioManager {
    //=============================
    // Constants and static fields
    //=============================
    private static final String REQUESTER_FILE = "requester.dat";
    private static final String PRODUCT_FILE = "product.dat";
    private static final String RELEASE_FILE = "release.dat";
    private static final String CHANGE_ITEM_FILE = "change-item.dat";
    private static final String CHANGE_REQUEST_FILE = "change-request.dat";
    private static final int LOCAL_DATE_LENGTH = 10;

    //=============================
    // Member fields
    //=============================
    private final RandomAccessFile requesterFile;
    private final RandomAccessFile productFile;
    private final RandomAccessFile releaseFile;
    private final RandomAccessFile changeItemFile;
    private final RandomAccessFile changeRequestFile;

    //=============================
    // Constructor
    //=============================

    //-----------------------------
    /**
     * Default construction for scenario manager, opens all files.
     */
    //---
    public ScenarioManager() throws IOException {
        requesterFile = new RandomAccessFile(REQUESTER_FILE, "rw");
        productFile = new RandomAccessFile(PRODUCT_FILE, "rw");
        releaseFile = new RandomAccessFile(RELEASE_FILE, "rw");
        changeItemFile = new RandomAccessFile(CHANGE_ITEM_FILE, "rw");
        changeRequestFile = new RandomAccessFile(CHANGE_REQUEST_FILE, "rw");
    }

    //=============================
    // Methods
    //=============================

    //-----------------------------
    /**
     * Get the size of requester file.
     *
     * @return (out) long - requester file size
     * @throws IOException
     */
    //---
    public long getRequesterFileSize() throws IOException {
        return requesterFile.length();
    }

    //-----------------------------
    /**
     * Get the size of product file.
     *
     * @return (out) long - product file size
     * @throws IOException
     */
    //---
    public long getProductFileSize() throws IOException {
        return productFile.length();
    }

    //-----------------------------
    /**
     * Helper function to pad character array with spaces to ensure
     * it's of desired length.
     *
     * @param charArray (in) char[] - character array to pad.
     * @param padLength (in) int - length of new character array.
     * @return (out) char[] - character array with padded spaces.
     */
    //---
    public static char[] padCharArray(char[] charArray, int padLength) {
        char[] temp = new char[padLength];

        if (charArray.length > padLength) {
            for (int i = 0; i < charArray.length; i++) {
                temp[i] = charArray[i];
            }
        } else {
            // Copy all characters from productName
            for (int i = 0; i < charArray.length; i++) {
                temp[i] = charArray[i];
            }

            // Pad with spaces to max length
            for (int i = charArray.length; i < padLength; i++) {
                temp[i] = ' ';
            }
        }
        return temp;
    }

    //-----------------------------
    /**
     * Helper function to read char arrays from file.
     *
     * @param file (in) RandomAccessFile - file to read char array from.
     * @param numChars (in) int - number of bytes the char array consists of.
     * @return (out) char[] - character array read from file
     */
    //---
    public static char[] readCharsFromFile(RandomAccessFile file, int numChars) throws IOException {
        char[] temp = new char[numChars];

        //reads each byte
        for (int i = 0; i < numChars; i++) {
            temp[i] = file.readChar();
        }
        return temp;
    }

    //-----------------------------
    /**
     * Helper function to read local dates from file.
     *
     * @param file (in) RandomAccessFile - file to read local date from.
     * @return (out) LocalDate - date from file.
     */
    //---
    public static LocalDate readDateFromFile(RandomAccessFile file) throws IOException {
        char[] temp = readCharsFromFile(file, LOCAL_DATE_LENGTH);
        String date = new String(temp).trim(); // remove white spaces

        if (date.isEmpty()) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    //-----------------------------
    /**
     * Adds a new requester to file.
     *
     * @param email (in) String - Email of new requester.
     * @param name (in) String - Name of new requester.
     * @param phoneNumber (in) int - Phone number of new requester.
     * @param department (in) String - Department of new requester. This can be left as empty.
     */
    //---
    public void addRequester(String email, String name, long phoneNumber, String department) {
        try {
            boolean requesterExists = Requester.requesterExists(requesterFile, email);

            if (!requesterExists) {
                Requester requester = new Requester(email, name, phoneNumber, department);
                requesterFile.seek(requesterFile.length());
                requester.writeRequester(requesterFile);
                System.out.println("The new requester is successfully added.");
            } else {
                System.out.println("Error: requester email already exists");
            }
        } catch (IOException e) {
            System.err.println("Error writing requester to file " + e.getMessage());
        }
    }

    //-----------------------------
    /**
     * Adds a new product to file.
     *
     * @param productName (in) String - Name of the new product.
     */
    //---
    public void addProduct(String productName) {
        try {
            boolean productExists = Product.productExists(productFile, productName);

            if (!productExists) {
                Product product = new Product(productName);
                productFile.seek(productFile.length());
                product.writeProduct(productFile);
                System.out.println("The new product has been added.");
            } else {
                System.out.println("Error: product name already exists");
            }
        } catch (IOException e) {
            System.err.println("Error writing product to file " + e.getMessage());
        }
    }

    //-----------------------------
    /**
     * Adds a new request to the file.
     *
     * @param changeID (in) int - Identifier of the ChangeItem this request is for.
     * @param productName (in) String - Name of product of change request.
     * @param reportedRelease (in) String - Release version of the product
     * @param requesterEmail (in) String - Email of the requester.
     * @param reportedDate (in) LocalDate - Date of when the request was made.
     */
    //---
    public void addChangeRequest(int changeID, String productName, String reportedRelease,
                                 String requesterEmail, LocalDate reportedDate) {
        ChangeRequest changeRequest = new ChangeRequest(changeID, productName,
                reportedRelease, requesterEmail, reportedDate);
        ChangeRequest compare = new ChangeRequest(0, "",
                "", "", LocalDate.of(2024,8,1));
        try {
            int pos = 0;
            while(pos < changeRequestFile.length() && changeRequestFile.length() != 0){
                changeRequestFile.seek(pos);
                compare.readChangeRequest(changeRequestFile);
                if(compare.getChangeID() == changeRequest.getChangeID()
                        && Arrays.equals(compare.getRequesterEmail(), changeRequest.getRequesterEmail())){
                    System.out.println("A change request of for this Change Item has already been submitted by this requester");
                    return;
                }
                pos += ChangeRequest.BYTES_SIZE_CHANGE_REQUEST;
            }
            changeRequestFile.seek(changeRequestFile.length());
            changeRequest.writeChangeRequest(changeRequestFile);
            System.out.println("New change request has been added!");
        } catch (IOException e) {
            System.err.println("Error writing request to file " + e.getMessage());
        }
    }

    //-----------------------------
    /**
     * Adds a new change item to the file.
     *
     * @param releaseID (in) int - Identifier of the release this change item is for.
     * @param productName (in) String - Product name of reported change item.
     * @param changeDescription (in) String - Change description of change.
     * @param priority (in) int - Priority of the change.
     * @param status (in) String - Status of the change.
     * @param anticipatedReleaseDate (in) LocalDate - Date of anticipated release date.
     */
    //---
    public void addChangeItem(String productName, String releaseID, String changeDescription, char priority,
                              String status, LocalDate anticipatedReleaseDate) {
        int changeID;
        try {
            if (changeItemFile.length() == 0) {
                changeID = 0;
            } else {
                ChangeItem dummy = new ChangeItem();
                changeItemFile.seek(changeItemFile.length() - ChangeItem.BYTES_SIZE_CHANGE_ITEM);
                dummy.readChangeItems(changeItemFile);
                changeID = dummy.getChangeID() + 1;
            }
            ChangeItem changeItem = new ChangeItem(changeID, productName, releaseID, changeDescription,
                    priority, status, anticipatedReleaseDate);
            changeItemFile.seek(changeItemFile.length());
            changeItem.writeChangeItem(changeItemFile);
        } catch (IOException e) {
            System.err.println("Error writing change item to file " + e.getMessage());
        }
    }

    //-----------------------------
    /**
     * Modifies a specific change item in the file.
     *
     * @param changeID (in) int - Change ID reference to be searched in file.
     * @param modifiedChangeItem (in) ChangeItem - The new modified change item to be written
     *                          into file.
     */
    //---
    public void modifyChangeItem(int changeID, ChangeItem modifiedChangeItem) {
        ChangeItem change = new ChangeItem();
        int pos = 0;

        try {
            changeItemFile.seek(pos);
            //locate correct ChangeItem from file
            while (true){
                change.readChangeItems(changeItemFile);

                if (changeID == change.getChangeID()){
                    break;
                }
                pos += ChangeItem.BYTES_SIZE_CHANGE_ITEM;
            }
            changeItemFile.seek(pos);
            modifiedChangeItem.writeChangeItem(changeItemFile);
        } catch (IOException e) {
            System.err.println("Error modifying change item to file " + e.getMessage());
        }
    }

    // *********TEMPORARY FOR UNIT TEST, DELETE LATER
    public void modifyChangeItem(RandomAccessFile myfile,int changeID, ChangeItem modifiedChangeItem) {
        ChangeItem change = new ChangeItem();
        int pos = 0;

        try {
            myfile.seek(pos);
            //locate correct ChangeItem from file
            while (true){
                change.readChangeItems(myfile);

                if (changeID == change.getChangeID()){
                    break;
                }
                pos += ChangeItem.BYTES_SIZE_CHANGE_ITEM;
            }
            myfile.seek(pos);
            modifiedChangeItem.writeChangeItem(myfile);
        } catch (IOException e) {
            System.err.println("Error modifying change item to file " + e.getMessage());
        }
    }

    //-----------------------------
    /**
     * Modifies a specific release in the file.
     *
     * @param releaseID (in) String - Release ID reference to be searched in file.
     * @param modifiedRelease (in) Release - The new modified release to be written into file.
     */
    //---
    public void modifyRelease(String releaseID, Release modifiedRelease) {
        Release fileRelease = new Release();
        long pos = 0;

        try {
            releaseFile.seek(pos);
            // locate correct Release from file
            while (true){
                fileRelease.readRelease(releaseFile);
                // convert char[] to String
                String releaseIDToBeChanged = new String(fileRelease.getReleaseID());
                if (releaseID.equals(releaseIDToBeChanged)) {
                    break;
                }
                pos += Release.BYTES_SIZE_RELEASE;
            }
            releaseFile.seek(pos);
            modifiedRelease.writeRelease(releaseFile);
        } catch (IOException e) {
            System.err.println("Error modifying release to file " + e.getMessage());
        }
    }

    //-----------------------------
    /**
     * Adds a new release to the file.
     *
     * @param productName (in) String - Identifier of the product this release is for.
     * @param releaseID (in) String - Identifier for the release
     * @param date (in) LocalDate - Date of release
     */
    //---
    public void addRelease(String productName, String releaseID, LocalDate date) {
        try {
            boolean releaseExists = Release.releaseExists(releaseFile, releaseID);

            if (!releaseExists) {
                Release release = new Release(productName, releaseID, date);
                releaseFile.seek(releaseFile.length());
                release.writeRelease(releaseFile);
                System.out.println("The new release ID has been added.");
            } else {
                System.out.println("Error: release ID already exists");
            }
        } catch (IOException e) {
            System.err.println("Error writing release to file " + e.getMessage());
        }
    }

    //-----------------------------
    /**
     * Gets a list of Requesters from the file to display for user.
     *
     * @param page (in) int - Counter to track what page of Requester are displayed for user.
     * @param pageSize (in) int - How many items of data each page can hold.
     * @return (out) String[] - String array of emails.
     */
    //---
    public String[] generateRequesterPage(int page, int pageSize) {
        long startingPage = page * pageSize * Requester.BYTES_SIZE_REQUESTER;
        String[] emails = new String[pageSize];
        Requester r = new Requester();

        try {
            requesterFile.seek(startingPage);
        } catch (IOException e) {
        System.err.println("Error in reading from file" + e.getMessage());
        }

        for (int i = 0; i < 6; i++) {
            try {
                r.readRequester(requesterFile);
                emails[i] = new String(r.getEmail());
            } catch (EOFException e) {
                // do nothing when end of file is reached
            } catch (IOException e) {
                System.err.println("Error in reading from file" + e.getMessage());
            }
        }
        return emails;
    }

    //-----------------------------
    /**
     * Gets a list of Products from the file to display for user.
     *
     * @param page (in) int - Counter to track what page of Product are displayed for user.
     * @param pageSize (in) int - How many items of data each page can hold.
     * @return String[] (out) - String array of product names
     */
    //---
    public String[] generateProductPage(int page, int pageSize) {
        String[] productNames = new String[pageSize];
        long startingPage = page * pageSize * Product.BYTES_SIZE_PRODUCT;
        Product p = new Product();

        try {
            productFile.seek(startingPage);
        } catch (IOException e) {
            System.err.println("Error in finding product page" + e.getMessage());
        }

        for (int i = 0; i < 6; i++) {
            try {
                p.readProduct(productFile);
                productNames[i] = new String(p.getProductName());
            } catch (EOFException e) {
                // do nothing at end of file
            } catch (IOException e) {
                System.err.println("Error in reading from file" + e.getMessage());
            }
        }
        return productNames;
    }

    //-----------------------------
    /**
     * Gets a list of Valid Releases from the file to display for user.
     *
     * @param productName (in) String - productName of specified release.
     * @param lastReleaseName (in) String - last release of previous page.
     * @param pageSize (in) int - How many items of data each page can hold.
     * @return (out) String[] - String array of releases of specific product.
     */
    //---
    public String[] generateReleasePage(String productName, String lastReleaseName, int pageSize) {
        String[] releaseVersions = new String[pageSize];
        Release r = new Release();

        // get the starting position in file
        try {
            long startPosition = getStartingPositionForReleaseItem(lastReleaseName);
            releaseFile.seek(startPosition);
        } catch (IOException e) {
            System.err.println("Error in finding release page" + e.getMessage());
        }

        int releaseCounter = 0;
        while (releaseCounter < pageSize) {
            try {
                r.readRelease(releaseFile);
                String temp = new String(r.getProductName());
                if (temp.equals(productName)) {
                    releaseVersions[releaseCounter] = new String(r.getReleaseID());
                    releaseCounter++;
                }
            } catch (EOFException e) {
                break;
            } catch (IOException e) {
                System.err.println("Error in reading from file" + e.getMessage());
            }
        }
        return releaseVersions;
    }

    //-----------------------------
    /**
     * Utility method that searches the last release of the previous page, and gets its position
     * in the file.
     *
     * @param lastReleaseName (in) String - last release of previous page.
     * @return (out) long - position in number of bytes
     * @throws IOException
     */
    //---
    private long getStartingPositionForReleaseItem(String lastReleaseName) throws IOException {
        Release release = new Release();
        long pos = 0;
        releaseFile.seek(pos);

        if (lastReleaseName != null) {
            while (true) {
                release.readRelease(releaseFile);
                String startingPositionOfRelease = new String(release.getReleaseID());
                if (lastReleaseName.equals(startingPositionOfRelease)) {
                    break;
                }
                pos += Release.BYTES_SIZE_RELEASE;
            }
            pos += Release.BYTES_SIZE_RELEASE;
        }
        return pos;
    }

    //-----------------------------
    /**
     * Gets a list of Valid ChangeItem from the file to display for user.
     *
     * @param productName (in) String - Product name for specified change item.
     * @param releaseID (in) String - Release ID for specified change item.
     * @param lastChangeItem (in) int - last change item of previous page.
     * @param pageSize (in) int - How many items of data each page can hold.
     * @return (out) ChangeItem[] - array of change items
     */
    //---
    public ChangeItem[] generateChangeItemPage(String productName, String releaseID, int lastChangeItem, int pageSize) {
        ChangeItem[] changeItems = new ChangeItem[pageSize];

        // get the starting position in file
        try {
            long startingPosition = getStartingPositionForChangeItem(lastChangeItem);
            changeItemFile.seek(startingPosition);
        } catch (IOException e) {
            System.err.println("Error in finding change item page" + e.getMessage());
        }

        int changeItemCounter = 0;
        while (changeItemCounter < pageSize) {
            try {
                ChangeItem c = new ChangeItem();
                c.readChangeItems(changeItemFile);

                String tempProductName = new String(c.getProductName());
                String tempReleaseID = new String(c.getReleaseID());

                if (tempProductName.equals(productName) && tempReleaseID.equals(releaseID)) {
                    changeItems[changeItemCounter] = c;
                    changeItemCounter++;
                }
            } catch (EOFException e) {
                break;
            } catch (IOException e) {
                System.err.println("Error in reading from file" + e.getMessage());
            }
        }
        return changeItems;
    }

    //-----------------------------
    /**
     * Utility method that searches the last change item of the previous page, and gets its position
     * in the file.
     *
     * @param lastChangeItem (in) int - last change item of previous page.
     * @return (out) long - position in number of bytes
     * @throws IOException
     */
    //---
    private long getStartingPositionForChangeItem(int lastChangeItem) throws IOException {
        ChangeItem change = new ChangeItem();
        long pos = 0;
        changeItemFile.seek(pos);

        if (lastChangeItem != -1) {
            while (true) {
                change.readChangeItems(changeItemFile);
                int changeItemOfStartingPosition = change.getChangeID();
                if (lastChangeItem == changeItemOfStartingPosition) {
                    break;
                }
                pos += ChangeItem.BYTES_SIZE_CHANGE_ITEM;
            }
            pos += ChangeItem.BYTES_SIZE_CHANGE_ITEM;
        }
        return pos;
    }

    //-----------------------------
    /**
     * Get s a list of all filtered change items of a specific product.
     *
     * @param productName (in) String - Product name reference to find all pending changes.
     * @param lastChangeItem (in) int - Last change item of previous page.
     * @param pageSize (in) int - How many items of data each page can hold.
     * @param mode (in) String - type of filtering.
     * @return (out) ChangeItem[] - array of filtered change items
     */
    //---
    public ChangeItem[] generateFilteredChangesPage(String productName, int lastChangeItem, int pageSize, String mode) {
        ChangeItem[] changeItems = new ChangeItem[pageSize];

        try {
            long startingPosition = getStartingPositionForChangeItem(lastChangeItem);
            changeItemFile.seek(startingPosition);
        } catch (IOException e) {
            System.err.println("Error in finding change item page" + e.getMessage());
        }

        int changeItemCounter = 0;
        while (changeItemCounter < pageSize) {
            try {
                ChangeItem c = new ChangeItem();
                c.readChangeItems(changeItemFile);

                String tempProductName = new String(c.getProductName());
                String tempStatus = new String(c.getStatus()).trim();
                boolean validChange;
                if (mode.equals("pending")) {
                    validChange = tempProductName.equals(productName) &&
                            (!tempStatus.equals("Completed") && !tempStatus.equals("Cancelled"));
                } else {
                    validChange = tempProductName.equals(productName) &&
                            (tempStatus.equals("Completed"));
                }
                if (validChange) {
                    changeItems[changeItemCounter] = c;
                    changeItemCounter++;
                }
            } catch (EOFException e) {
                break;
            } catch (IOException e) {
                System.err.println("Error in reading from file" + e.getMessage());
            }
        }
        return changeItems;
    }

    //-----------------------------
    /**
     * Gets a list of all completed changes for customer notification.
     * Gets a list of all requester emails & names for a specific change item
     * tracked by change ID
     *
     * @param changeID (in) int - Change item reference.
     * @param lastEmail (in) String - Last email of previous page.
     * @param pageSize (in) int - How many items of data each page can hold.
     * @return (out) Requester[] - Requester array of requesters to notify.
     */
    //---
    public Requester[] generateEmailsPage(int changeID, String lastEmail, int pageSize) {
        Requester[] emails = new Requester[pageSize];
        String compEmail; // compared email from change request file

         ChangeRequest request = new ChangeRequest();

        // get the starting position in file
        try {
            long startPosition = getStartingPositionForChangeRequest(lastEmail);
            changeRequestFile.seek(startPosition);
        } catch (IOException e) {
            System.err.println("Error in finding change request page" + e.getMessage());
        }

        int itemCounter = 0;

        while (itemCounter < pageSize) {
            try {
                request.readChangeRequest(changeRequestFile);
                if (request.getChangeID() == changeID) {
                    compEmail = new String(request.getRequesterEmail());
                    Requester tempRequester = findRequesterByEmail(compEmail);
                    if (tempRequester != null) {
                        emails[itemCounter] = tempRequester;
                        itemCounter++;
                    }
                }
            } catch (EOFException e) {
                break;
            } catch (IOException e) {
                System.err.println("Error in reading file" + e.getMessage());
            }
        }
        return emails;
    }

    //-----------------------------
    /**
     * Searches requester file for specific email.
     *
     * @param email (in) String - email of requester.
     * @return (out) Requester - The requester object.
     * @throws IOException
     */
    //---
    private Requester findRequesterByEmail(String email) throws IOException {
        requesterFile.seek(0); // Start at the beginning of the requester file
        Requester requester = new Requester();

        try {
            while (true) {
                requester.readRequester(requesterFile);
                String requesterEmail = new String(requester.getEmail());
                if (email.equals(requesterEmail)) {
                    return requester;
                }
            }
        } catch (EOFException e) {
            // End of file reached, requester not found
        }
        return null;
    }

    //-----------------------------
    /**
     * Utility method that searches the last requester of the previous page, and gets its position
     * in the file.
     *
     * @param lastEmail (in) int - last change item of previous page.
     * @return (out) long - position in number of bytes
     * @throws IOException
     */
    //---
    private long getStartingPositionForChangeRequest(String lastEmail) throws IOException {
        ChangeRequest request = new ChangeRequest();
        long pos = 0;
        changeRequestFile.seek(pos);

        if (lastEmail != null) {
            while (true) {
                request.readChangeRequest(changeRequestFile);
                String startingPositionOfChangeRequest = new String(request.getRequesterEmail());
                if (lastEmail.equals(startingPositionOfChangeRequest)) {
                    break;
                }
                pos += ChangeRequest.BYTES_SIZE_CHANGE_REQUEST;
            }
            pos += ChangeRequest.BYTES_SIZE_CHANGE_REQUEST;
        }
        return pos;
    }

    //-----------------------------
    /**
     * Closes the file, on system shut down
     */
    //---
    public void closeFiles() {
        try {
            requesterFile.close();
        } catch (IOException e) {
            System.err.println("Error closing files " + e.getMessage());
        }
    }
}
