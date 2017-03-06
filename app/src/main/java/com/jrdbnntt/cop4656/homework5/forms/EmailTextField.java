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
    boolean checkField() {
        String input = this.input.getText().toString();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        return !this.required || emailPattern.matcher(input).matches();
    }
}
