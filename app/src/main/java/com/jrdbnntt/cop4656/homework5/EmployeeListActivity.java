package com.jrdbnntt.cop4656.homework5;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class EmployeeListActivity extends ListActivity {

    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor cursor = getContentResolver().query(
                HwContentProvider.CONTENT_URI, null, null, null, null
        );

        String[] listColumns = new String[] {
                HwContentProvider.Employee.ID,
                HwContentProvider.Employee.NAME,
                HwContentProvider.Employee.GENDER,
                HwContentProvider.Employee.EMAIL_ADDRESS,
                HwContentProvider.Employee.ACCESS_CODE,
                HwContentProvider.Employee.DEPARTMENT,
                HwContentProvider.Employee.AD_ACCESS
        };
        int[] listItems = new int[] {
                R.id.tvEmployeeIdValue,
                R.id.tvNameValue,
                R.id.tvGenderValue,
                R.id.tvEmailAddressValue,
                R.id.tvAccessCodeValue,
                R.id.tvDepartmentValue,
                R.id.tvAdAccessValue
        };

        adapter = new SimpleCursorAdapter(
                this, R.layout.employee_list_item, cursor, listColumns, listItems
        );
        setListAdapter(adapter);

        // Delete Dialog on click
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String employeeId =
                        ((TextView) view.findViewById(R.id.tvEmployeeIdValue)).getText().toString();
                promptDelete(employeeId);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iEnterData:
                // Go to data view activity
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }

        return true;
    }

    public void promptDelete(final String employeeId) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Delete it!
                        String selectionClause = HwContentProvider.Employee.ID + " = ?";
                        String[] selectionArgs = new String[] { employeeId };
                        int rowsDeleted = getContentResolver().delete(
                                HwContentProvider.CONTENT_URI,
                                selectionClause,
                                selectionArgs
                        );

                        String message;
                        if (rowsDeleted == 1) {
                            message = "Deleted Employee with id " + employeeId;
                        } else {
                            message = "Error: Unable to delete employee";
                        }

                        Toast.makeText(
                                getApplicationContext(),
                                message,
                                Toast.LENGTH_SHORT
                        ).show();

                        // Refresh list view
                        adapter.swapCursor(getContentResolver().query(
                                HwContentProvider.CONTENT_URI, null, null, null, null
                        ));

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete Employee with id '"+employeeId+"'?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }
}
