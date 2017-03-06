package com.jrdbnntt.cop4656.homework5.forms;


import android.widget.CheckBox;
import android.widget.TextView;

public class CheckBoxField extends Field<Boolean> {

    private CheckBox checkBox;

    public CheckBoxField(TextView label, CheckBox checkBox) {
        super(label, false);
        this.checkBox = checkBox;
    }

    @Override
    public void clearField() {
        this.checkBox.setChecked(false);
    }

    @Override
    boolean isBlank() {
        return this.checkBox.isChecked();
    }

    @Override
    Boolean getValue() {
        return this.checkBox.isChecked();
    }
}
