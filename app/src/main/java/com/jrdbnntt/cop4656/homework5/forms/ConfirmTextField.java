package com.jrdbnntt.cop4656.homework5.forms;

import android.widget.EditText;
import android.widget.TextView;


public class ConfirmTextField extends TextField {
    TextField fieldToMatch;

    public ConfirmTextField(TextView label, EditText input, TextField fieldToMatch) {
        super(label, input);
        this.fieldToMatch = fieldToMatch;
    }

    @Override
    boolean checkField() {
        String inputText = input.getText().toString();
        String confirmText = fieldToMatch.input.getText().toString();
        return inputText.length() > 0 && inputText.equals(confirmText);
    }
}
