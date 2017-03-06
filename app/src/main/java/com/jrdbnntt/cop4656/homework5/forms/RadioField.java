package com.jrdbnntt.cop4656.homework5.forms;


import android.widget.RadioButton;
import android.widget.TextView;

public class RadioField extends Field {
    private RadioButton[] radioButtons;

    public RadioField(TextView label, RadioButton[] radioButtons) {
        super(label);
        this.radioButtons = radioButtons;
    }

    @Override
    public void clearField() {
        for (RadioButton btn : this.radioButtons) {
            btn.setChecked(false);
        }
    }

    @Override
    boolean checkField() {
        if (!required) {
            return true;
        }

        for (RadioButton btn : radioButtons) {
            if (btn.isChecked()) {
                // At least one is checked
                return true;
            }
        }

        return false;
    }
}
