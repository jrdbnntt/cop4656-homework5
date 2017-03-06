package com.jrdbnntt.cop4656.homework5.forms;

public class Form {

    private Field[] fields;

    public Form(Field[] fields) {
        this.fields = fields;
    }

    public void validate() throws FieldValidationException {
        for (Field field : this.fields) {
            field.validate();
        }
    }

    public void clear() {
        for (Field field : this.fields) {
            field.clear();
        }
    }
}
