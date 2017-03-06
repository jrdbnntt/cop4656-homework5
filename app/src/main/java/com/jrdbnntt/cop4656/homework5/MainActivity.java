package com.jrdbnntt.cop4656.homework5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jrdbnntt.cop4656.homework5.forms.CheckBoxField;
import com.jrdbnntt.cop4656.homework5.forms.ConfirmTextField;
import com.jrdbnntt.cop4656.homework5.forms.EmailTextField;
import com.jrdbnntt.cop4656.homework5.forms.Field;
import com.jrdbnntt.cop4656.homework5.forms.FieldValidationException;
import com.jrdbnntt.cop4656.homework5.forms.Form;
import com.jrdbnntt.cop4656.homework5.forms.RadioField;
import com.jrdbnntt.cop4656.homework5.forms.SelectField;
import com.jrdbnntt.cop4656.homework5.forms.TextField;


public class MainActivity extends AppCompatActivity {

    private static final int[] layouts = {
            R.layout.linear,
            R.layout.relative,
            R.layout.table
    };
    private static int currentLayout = 0;

    private Form form;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(layouts[currentLayout]);
    }

    void init(int layout) {
        setContentView(layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize form management
        TextField accessCode = new TextField((TextView) findViewById(R.id.tvAccessCode), (EditText) findViewById(R.id.etAccessCode));
        this.form = new Form(new Field[] {
                new TextField((TextView) findViewById(R.id.tvEmployeeId), (EditText) findViewById(R.id.etEmployeeId),
                        getApplicationContext().getResources().getStringArray(R.array.employeeIds)),
                new TextField((TextView) findViewById(R.id.tvName), (EditText) findViewById(R.id.etName)),
                new RadioField((TextView) findViewById(R.id.tvGender), new RadioButton[] {
                        (RadioButton) findViewById(R.id.rbFemale),
                        (RadioButton) findViewById(R.id.rbMale)
                }),
                new EmailTextField((TextView) findViewById(R.id.tvEmailAddress), (EditText) findViewById(R.id.etEmailAddress)),
                accessCode,
                new ConfirmTextField((TextView) findViewById(R.id.tvAccessCodeConfirm), (EditText) findViewById(R.id.etAccessCodeConfirm), accessCode),
                new SelectField((TextView) findViewById(R.id.tvDepartment), (Spinner) findViewById(R.id.sDepartment), R.array.departments),
                new CheckBoxField((TextView) findViewById(R.id.tvAdAccess), (CheckBox) findViewById(R.id.cbAdAccess))
        });

        // Setup button click listeners
        Button resetButton = (Button) findViewById(R.id.bReset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the form
                form.clear();
            }
        });

        Button submitButton = (Button) findViewById(R.id.bSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message =  "Data successfully submitted to database";

                try {
                    form.validate();
                } catch (FieldValidationException e) {
                    message = e.getMessage();
                }

                Toast.makeText(
                        getApplicationContext(),
                        message,
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        Button changeViewButton = (Button) findViewById(R.id.bChangeView);
        changeViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switch to next view
                currentLayout += 1;
                if (currentLayout >= layouts.length) {
                    currentLayout = 0;
                }
                init(layouts[currentLayout]);
            }
        });

    }

}
