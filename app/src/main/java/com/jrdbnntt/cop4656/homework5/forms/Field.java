package com.jrdbnntt.cop4656.homework5.forms;

import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;

import com.jrdbnntt.cop4656.homework5.R;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Field<ValueType> {

    private TextView label;
    protected boolean required = true;
    private ArrayList<FieldValidator<ValueType>> extraValidators;

    Field(TextView label) {
        this.label = label;
    }


    Field(TextView label, boolean required) {
        this.label = label;
        this.required = required;
    }

    Field(TextView label, boolean required, FieldValidator<ValueType> extraValidator) {
        this.label = label;
        this.required = required;
        this.extraValidators = new ArrayList<>(1);
        this.extraValidators.add(extraValidator);

    }

    Field(TextView label, boolean required, FieldValidator<ValueType>[] extraValidators) {
        this.label = label;
        this.required = required;
        this.extraValidators = new ArrayList<>(Arrays.asList(extraValidators));
    }

    protected void checkField() throws FieldValidationException {}
    abstract void clearField();
    abstract boolean isBlank();
    abstract ValueType getValue();

    public void clear() {
        this.setError(false);
        this.clearField();
    }

    public void validate() throws FieldValidationException {
        if (isBlank()) {
            if (required) {
                setError(true);
                throw FieldValidationException.REQUIRED(this);
            }
            return;
        }

        try {
            checkField();

            if (this.extraValidators != null) {
                ValueType value = getValue();
                for (FieldValidator<ValueType> validator : extraValidators) {
                    validator.validate(value);
                }
            }

        } catch (FieldValidationException e) {
            setError(true);
            throw e;
        }
        setError(false);
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

    public String getName() {
        return this.label.getText().toString();
    }

}