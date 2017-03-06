package com.jrdbnntt.cop4656.homework5.forms;


import android.widget.RadioButton;
import android.widget.TextView;

public class RadioField extends Field<RadioButton> {
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
    boolean isBlank() {
        return getSelected() == null;
    }

    @Override
    RadioButton getValue() {
        return getSelected();
    }

    private RadioButton getSelected() {
        for (RadioButton btn : radioButtons) {
            if (btn.isChecked()) {
                return btn;
            }
        }
        return null;
    }

}
