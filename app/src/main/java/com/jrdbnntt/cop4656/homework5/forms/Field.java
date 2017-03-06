package com.jrdbnntt.cop4656.homework5.forms;

import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;

import com.jrdbnntt.cop4656.homework5.R;

public abstract class Field {

    private TextView label;
    protected boolean required = true;

    Field(TextView label) {
        this.label = label;
    }


    Field(TextView label, boolean required) {
        this.label = label;
        this.required = required;
    }

    abstract boolean checkField();
    abstract void clearField();

    public void clear() {
        this.setError(false);
        this.clearField();
    }

    public boolean validate() {
        boolean isValid = checkField();
        setError(!isValid);
        return isValid;
    }

    protected void setError(boolean value) {
        if (value) {
            // Turn text red
            this.label.setTextColor(ResourcesCompat.getColor(
                    this.label.getContext().getResources(), R.color.colorLabelError, null)
            );
        } else {
            // Reset color to normal
            this.label.setTextColor(ResourcesCompat.getColor(
                    this.label.getContext().getResources(), R.color.colorLabelNormal, null)
            );
        }
    }

}