package com.jrdbnntt.cop4656.homework5.forms;


import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectField extends Field<String> {
    private Spinner spinner;
    private String emptyValue = "";

    public SelectField(TextView label, Spinner spinner, int arrayResourceId) {
        super(label);
        this.spinner = spinner;

        ArrayList<String> options = new ArrayList<>(
                Arrays.asList(spinner.getContext().getResources().getStringArray(arrayResourceId))
        );

        // Add empty value
        options.add(0, emptyValue);

        // Populate options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                spinner.getContext(), android.R.layout.simple_spinner_item, options
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
    }

    @Override
    boolean isBlank() {
        return spinner.getSelectedItem().toString().equals(emptyValue);
    }

    @Override
    String getValue() {
        return spinner.getSelectedItem().toString();
    }

    @Override
    void clearField() {
        spinner.setSelection(0);
    }
}
