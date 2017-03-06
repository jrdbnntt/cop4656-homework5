package com.jrdbnntt.cop4656.homework5.forms;

/**
 * Extra Validation on non-blank field values
 */
public interface FieldValidator<FieldType> {
    void validate(FieldType value) throws FieldValidationException;
}
