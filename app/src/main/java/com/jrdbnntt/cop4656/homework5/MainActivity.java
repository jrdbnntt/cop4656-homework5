package com.jrdbnntt.cop4656.homework5;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.jrdbnntt.cop4656.homework5.forms.FieldValidator;
import com.jrdbnntt.cop4656.homework5.forms.Form;
import com.jrdbnntt.cop4656.homework5.forms.RadioField;
import com.jrdbnntt.cop4656.homework5.forms.SelectField;
import com.jrdbnntt.cop4656.homework5.forms.TextField;


public class MainActivity extends AppCompatActivity {

    // Layout Management
    private static final int[] layouts = {
            R.layout.linear,
            R.layout.relative,
            R.layout.table
    };
    private static int currentLayout = 0;

    // Field Management
    private Form form;
    private TextField employeeIdField, nameField, accessCodeField;
    private RadioField genderField;
    private EmailTextField emailField;
    private CheckBoxField adAccessField;
    private SelectField departmentField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(layouts[currentLayout]);
    }

    void init(int layout) {
        setContentView(layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initForm();
        initButtons();
    }

    /**
     * Initialize & configure fields and form
     */
    void initForm() {
        // Initialize
        employeeIdField = new TextField(
                (TextView) findViewById(R.id.tvEmployeeId),
                (EditText) findViewById(R.id.etEmployeeId)
        );
        nameField = new TextField(
                (TextView) findViewById(R.id.tvName),
                (EditText) findViewById(R.id.etName)
        );

        genderField = new RadioField((TextView) findViewById(R.id.tvGender), new RadioButton[] {
                (RadioButton) findViewById(R.id.rbFemale),
                (RadioButton) findViewById(R.id.rbMale)
        });
        emailField = new EmailTextField(
                (TextView) findViewById(R.id.tvEmailAddress),
                (EditText) findViewById(R.id.etEmailAddress)
        );
        accessCodeField = new TextField(
                (TextView) findViewById(R.id.tvAccessCode),
                (EditText) findViewById(R.id.etAccessCode)
        );
        ConfirmTextField confirmAccessCodeField = new ConfirmTextField(
                (TextView) findViewById(R.id.tvAccessCodeConfirm),
                (EditText) findViewById(R.id.etAccessCodeConfirm),
                accessCodeField
        );
        departmentField = new SelectField(
                (TextView) findViewById(R.id.tvDepartment),
                (Spinner) findViewById(R.id.sDepartment),
                R.array.departments
        );
        adAccessField = new CheckBoxField(
                (TextView) findViewById(R.id.tvAdAccess),
                (CheckBox) findViewById(R.id.cbAdAccess)
        );
        form = new Form(new Field[] {
                employeeIdField,
                nameField,
                genderField,
                emailField,
                accessCodeField,
                confirmAccessCodeField,     // Purely used for validation
                departmentField,
                adAccessField
        });

    }

    /**
     * Setup button click listeners
     */
    void initButtons() {
        // Form reset button
        Button resetButton = (Button) findViewById(R.id.bReset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the form
                form.clear();
            }
        });

        // View changer button
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

        // Submit button, just validates
        Button submitButton = (Button) findViewById(R.id.bSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message =  "Data successfully submitted to database";

                try {
                    form.validate();

                    employeeIdField.validateWith(new FieldValidator<Editable>() {
                        @Override
                        public void validate(Editable value) throws FieldValidationException {
                            if (!isExistingEmployee(value.toString())) {
                                throw new FieldValidationException(
                                        "Employee with given id does not exist in database."
                                );
                            }
                        }
                    });

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

        // Add/Register button. Validates + inserts or updates
        Button registerButton = (Button) findViewById(R.id.bAddRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message =  null;

                try {
                    form.validate();
                } catch (FieldValidationException e) {
                    message = e.getMessage();
                }

                if (message == null) {
                    String employeeId = employeeIdField.getValue().toString();

                    ContentValues values = getContentValues();
                    if (isExistingEmployee(employeeId)) {
                        // Update employee
                        String selectionClause = HwContentProvider.Employee.ID + " = ?";
                        String[] selectionArgs = new String[] { employeeId };
                        getContentResolver().update(
                                HwContentProvider.CONTENT_URI,
                                values,
                                selectionClause,
                                selectionArgs
                        );
                        message = "Existing employee information updated.";
                    } else {
                        // Insert new employee
                        getContentResolver().insert(HwContentProvider.CONTENT_URI, values);
                        message = "New employee registered.";
                    }
                }

                Toast.makeText(
                        getApplicationContext(),
                        message,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }


    /**
     * Checks database to see if an employee exists with the given id
     */
    boolean isExistingEmployee(String employeeId) {
        boolean exists;

        String selectionClause = HwContentProvider.Employee.ID + " = ?";
        String[] selectionArgs = new String[] { employeeId };
        Cursor cursor = getContentResolver().query(
                HwContentProvider.CONTENT_URI, null, selectionClause, selectionArgs, null
        );
        exists = cursor.getCount() != 0;
        cursor.close();

        return exists;
    }

    /**
     * Constructs a ContentValues object representation of the form fields' values
     */
    private ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(HwContentProvider.Employee.ID, employeeIdField.getValue().toString());
        values.put(HwContentProvider.Employee.NAME, nameField.getValue().toString());
        values.put(HwContentProvider.Employee.GENDER, genderField.getValue().getText().toString());
        values.put(HwContentProvider.Employee.EMAIL_ADDRESS, emailField.getValue().toString());
        values.put(HwContentProvider.Employee.ACCESS_CODE,
                Integer.parseInt(accessCodeField.getValue().toString()));
        values.put(HwContentProvider.Employee.DEPARTMENT, departmentField.getValue());
        values.put(HwContentProvider.Employee.AD_ACCESS, adAccessField.getValue()? 1 : 0);
        return values;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iViewData:
                // Go to data view activity
                Intent i = new Intent(this, EmployeeListActivity.class);
                startActivity(i);
                break;
        }

        return true;
    }
}
