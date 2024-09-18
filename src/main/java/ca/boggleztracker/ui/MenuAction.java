/**
 * File: MenuAction.java
 * Revision History:
 * - 2024-06-29: Function declarations
 * Purpose:
 * MenuAction functional interface defines a contract for menu actions. Any class implementing
 * this interface must override the performAction method. This method executes the specifc
 * action associated with the menu entry.
 */

package ca.boggleztracker.ui;

public interface MenuAction {
    //=============================
    // Abstract Methods
    //=============================

    //-----------------------------
    /**
     * Executes the specific action associated with a menu entry.
     * Must be overridden by classes implementing this interface.
     */
    //---
    void performAction();
}
