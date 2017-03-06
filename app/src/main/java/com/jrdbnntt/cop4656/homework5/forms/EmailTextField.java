package com.jrdbnntt.cop4656.homework5.forms;

import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class EmailTextField extends TextField {

    public EmailTextField(TextView label, EditText input) {
        super(label, input);
    }

    @Override
    protected void checkField() throws FieldValidationException {
        super.checkField();
        String text = this.input.getText().toString();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;

        if (!emailPattern.matcher(text).matches()) {
            throw FieldValidationException.INVALID(this);
        }
    }
}
