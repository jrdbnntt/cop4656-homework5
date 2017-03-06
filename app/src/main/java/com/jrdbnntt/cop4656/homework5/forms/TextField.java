package com.jrdbnntt.cop4656.homework5.forms;

import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

public class TextField extends Field<Editable> {

    EditText input;
    private String[] allowedOptions;

    public TextField(TextView label, EditText input) {
        super(label);
        this.input = input;
    }

    public TextField(TextView label, EditText input, String[] allowedOptions) {
        super(label);
        this.input = input;
        this.allowedOptions = allowedOptions;
    }

    @Override
    public void clearField() {
        input.setText("");
    }

    @Override
    boolean isBlank() {
        return input.getText().length() == 0;
    }

    @Override
    Editable getValue() {
        return input.getText();
    }

    private boolean isInOptions(String value) {
        if (allowedOptions == null) {
            return true;
        }

        for(String s : allowedOptions) {
            if (s.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void checkField() throws FieldValidationException {
        if (!isInOptions(getValue().toString())) {
            throw FieldValidationException.INVALID(this);
        }
    }
}
