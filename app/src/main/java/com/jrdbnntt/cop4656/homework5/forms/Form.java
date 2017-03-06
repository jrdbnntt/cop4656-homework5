package com.jrdbnntt.cop4656.homework5.forms;


public class Form {

    private Field[] fields;

    public Form(Field[] fields) {
        this.fields = fields;
    }

    public boolean validate() {
        boolean passed = true;

        for (Field field : this.fields) {
            if (!field.validate()) {
                passed = false;
            }
        }

        return passed;
    }

    public void clear() {
        for (Field field : this.fields) {
            field.clear();
        }
    }
}
