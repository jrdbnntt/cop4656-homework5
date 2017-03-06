package com.jrdbnntt.cop4656.homework5.forms;

import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

public class TextField extends Field<Editable> {

    EditText input;

    public TextField(TextView label, EditText input) {
        super(label);
        this.input = input;
    }

    @Override
    public void clearField() {
        input.setText("");
    }

    @Override
    public boolean isBlank() {
        return input.getText().length() == 0;
    }

    @Override
    public Editable getValue() {
        return input.getText();
    }

}
