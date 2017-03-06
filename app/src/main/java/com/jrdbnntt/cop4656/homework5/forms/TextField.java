package com.jrdbnntt.cop4656.homework5.forms;

import android.widget.EditText;
import android.widget.TextView;

public class TextField extends Field {

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
    boolean checkField() {
        String value = input.getText().toString();
        return !required || (value.length() > 0 && isInOptions(value));
    }
}
