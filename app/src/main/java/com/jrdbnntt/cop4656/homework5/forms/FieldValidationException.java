package com.jrdbnntt.cop4656.homework5.forms;


/**
 * To be raised when a field fails validation. Contains reason.
 */
public class FieldValidationException extends Exception {

    public FieldValidationException(String message) {
        super(message);
    }

    public static FieldValidationException REQUIRED(Field field) {
        return new FieldValidationException(field.getName() + " is required.");
    }

    public static FieldValidationException INVALID(Field field) {
        return new FieldValidationException(field.getName() + " is invalid.");
    }
}
