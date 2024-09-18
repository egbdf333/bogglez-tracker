/**
 * File: TextMenu.java
 * Revision History:
 * - 2024-06-29: Function and variable declarations
 * - 2024-07-09: Modified getSelection() method
 * - 2024:07-25: Documentation changes and moved static method above constructor 
 *  -2024-07-25: Modified error messages for invalid menu item input
 * Purpose:
 * TextMenu class is responsible for displaying and handling user input of the various menus.
 * This class uses an array of MenuEntry objects to represent menu options and their
 * corresponding actions.
 */
package ca.boggleztracker.ui;

import java.util.Scanner;

public class TextMenu {
    //=============================
    // Constants and Static Fields
    //=============================
    private static final int MINIMUM_SELECTION_NUMBER = 1;
    private boolean shouldRepeat;
    private static boolean shouldMainMenuRepeat = true;

    //=============================
    // Member fields
    //=============================
    private final String title;
    private final MenuEntry[] entries;

    //=============================
    // Utility classes
    //=============================

    //-----------------------------
    /**
     * MenuEntry is a utility class to bundle the name of the menu entry and the corresponding action.
     */
    //---
    public static class MenuEntry {
        //=============================
        // Member fields
        //=============================
        private final String text;
        private final MenuAction action;

        //=============================
        // Constructors
        //=============================

        //-----------------------------
        /**
         * Two argument constructor for MenuEntry.
         *
         * @param text (in) String - Name of the menu item.
         * @param action (in) MenuAction - Action of the menu item.
         */
        //---
        public MenuEntry(String text, MenuAction action) {
            this.text = text;
            this.action = action;
        }
    }

    //=============================
    // Constructors
    //=============================

    //-----------------------------

    /**
     * Three argument constructor for TextMenu.
     *
     * @param title (in) String - Name of the (sub)menu.
     * @param shouldRepeat (in) boolean - Boolean needed to determine if menu should repeat
     *                    after performing the action.
     * @param entries (in) MenuEntry[] - Array of menu entries associated with the specified menu.
     */
    //---
    public TextMenu(String title, boolean shouldRepeat, MenuEntry[] entries) {
        this.title = title;
        this.shouldRepeat = shouldRepeat;
        this.entries = entries;
    }

    //=============================
    // Static Methods
    //=============================

    //-----------------------------
    /**
     * Static method to set if the main menu should repeat. Special case called
     * in TextUI's exit system method.
     */
    //---
    public static void setShouldMainMenuRepeat(boolean shouldMainMenuRepeat) {
        TextMenu.shouldMainMenuRepeat = shouldMainMenuRepeat;
    }

    //=============================
    // Methods
    //=============================

    //-----------------------------
    /**
     * Displays the menu and performs the selected menu entry.
     */
    //---
    public void doMenu() {
        do {
            display();
            int option = getSelection();
            MenuAction action = entries[option - 1].action;

            if (action != null) {
                action.performAction();
            } else {
                shouldRepeat = false;
            }
            if (!shouldMainMenuRepeat) {
                shouldRepeat = false;
            }
        } while (shouldRepeat);
    }

    //-----------------------------
    /**
     * Responsible for getting user menu selection.
     *
     * @return (out) int - user input selection number.
     */
    //---
    public int getSelection() {return getNumberBetween(MINIMUM_SELECTION_NUMBER, entries.length);}

    /**
     * Gets a valid user input based on menu range.
     *
     * @param min (in) int - minimum number user input can be.
     * @param max (in) out - maximum number user input can be.
     * @return (out) int - returns the selection of user.
     */
    static public int getNumberBetween(int min, int max) {
        Scanner keyboard = new Scanner(System.in);
        int selection;

        while (true) {
            System.out.println("ENTER [" + min + "-" + max + "]:");
            System.out.print("> ");

            String input = keyboard.nextLine().trim();

            if(input.isEmpty()) {
                System.out.println("Error: Input can not be empty, please enter a integer between " + min + " and " + max);
                continue;
            }

            try {
                selection = Integer.parseInt(input);

                if (selection < min || selection > max){
                    System.out.println("Error: Please enter a selection between " + min + " and " + max);
                } else {
                    break;
                }
            } catch (NumberFormatException e) { //
                System.out.println("Error: Please enter a valid integer between " + min + " and " + max);
            }
        }
        return selection;
    }

    //-----------------------------
    /**
     * Displays the menu.
     */
    //---
    public void display() {
        System.out.println(title);

        for (int i = 0; i < entries.length; i++) {
            System.out.println(i + 1 + ") " + entries[i].text);
        }
    }
}
