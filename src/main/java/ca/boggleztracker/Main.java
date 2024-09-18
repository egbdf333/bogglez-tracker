/**
 * File: Main.java
 * Revision History:
 * - 2024-06-29: Function and variable declarations
 * Purpose:
 * Main class starts the bug tracker application. Data includes a static string
 * variable storing the data file. The class is responsible for instantiating the
 * manager and the user interface (UI). It uses dependency injection to pass a reference of the
 * manager to the UI for improved modularity and flexibility.
 */
package ca.boggleztracker;

import ca.boggleztracker.model.ScenarioManager;
import ca.boggleztracker.ui.TextUI;
import java.io.IOException;


public class Main {
    //=============================
    // Static Method Declarations
    //=============================

    //-----------------------------
    /**
     * System start up of the bug tracker application
     * @param args (in) String[] - Command line arguments
     */
    //---
    public static void main(String[] args) {
        try {
            ScenarioManager manager = new ScenarioManager();
            TextUI ui = new TextUI(manager);
            ui.start();
        } catch (IOException e) {
            System.err.println("Error opening record files " + e.getMessage());
        }
    }
}

//    Coding Conventions for ca.boggleztracker
//
//    File Header:
//    - Each class should start with a comment stating the file's name, revision history,
//      and a description of the class's purpose.
//    - Example:
//    /**
//    * File: Student.java
//    * Revision History:
//    * - 2024-06-26: Initial Version
//    * - 2024-06-27: Added ...
//    * Purpose:
//    * Student class models the information about university student. Data includes student name,
//    * and address. It supports reading in file, and writing out to a file.
//    */
//
//    "imports"
//
//    class Student {
//        ...
//    }
//
//    Section Dividers:
//    - For each major section it must be separated by a bolded dividing comment line
//    - Use thin dividing lines between each individual function, and //--- to mark the
//      end of comment. (Refer to Functions Comments and Parameters)
//    - Example:
//      //==============================
//      // Constants and Static Fields
//      //==============================
//
//    Function Comments and Parameters:
//    - Using Javadoc style, have general comments about a function above the function signature.
//    - Each function parameter should be commented with its name, type, and its parameter direction (in, out, in/out).
//    - Example:
//      //------------------------------
//      /**
//       * Retrieves a product by its name.
//       *
//       * @param name (in) String - The name of the product to be retrieved.
//       * @return (out) Product - The product with the specified name, or null if not found
//       */
//      //---
//
//    Naming Conventions:
//    - Constants must be all uppercase with underscores.
//    - Functions must start with a lower case letter, and use CamelCase
//    - Classes must start with an upper case letter, and use CamelCase
//
//    Indentation and Braces:
//    - Tab size is 4.
//    - Opening braces is placed on the same line as the function, class, or control statement.
//      closing braces is on its own line, lined up with the start of an opening enclosing statement
//    - All if statements and loops should include braces around their statements.
//
//    Statements and Spacing:
//    - Each variable is declared in its own definition. (ex. do not write int i, j;)
//    - All binary and ternary conditionals must be surrounded by one space.
//    - Unary operators have no additional space



