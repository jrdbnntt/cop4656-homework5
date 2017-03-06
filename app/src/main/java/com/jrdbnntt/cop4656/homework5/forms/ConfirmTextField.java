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
    protected void checkField() throws FieldValidationException {
        super.checkField();
        String inputText = this.getValue().toString();
        String confirmText = fieldToMatch.getValue().toString();

        if (!inputText.equals(confirmText)) {
            throw new FieldValidationException(
                    this.getName() + " does not match " + fieldToMatch.getName());
        }
    }
}
