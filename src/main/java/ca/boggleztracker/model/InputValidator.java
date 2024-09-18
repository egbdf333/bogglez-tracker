/**
 * File: InputValidator.java
 * Revision History:
 * - 2024-07-10: Function declarations
 * Purpose:
 * InputValidator functional interface defines a contract for validating length of inputs. Any class implementing
 * this interface must override the isValid method.
 */
package ca.boggleztracker.model;

public interface InputValidator {
    boolean isValid(String input, int length);
}
