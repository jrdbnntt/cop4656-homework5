package com.jrdbnntt.cop4656.homework5;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Single table content provider for handling Employee information.
 */
public class HwContentProvider extends ContentProvider {
    public final static String DATABASE_NAME = "Homework";
    public static final Uri CONTENT_URI = Uri.parse(
            "content://com.jrdbnntt.cop4656.homework5.provider"
    );
    public static final String SQL_CREATE_MAIN = Employee.getCreateSql();


    private MainDatabaseHelper helper;

    public static class Employee {
        private static final String TABLE_NAME = "Employee";

        // Column names
        public static final String _ID = "_id";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String GENDER = "gender";
        public static final String EMAIL_ADDRESS = "email_address";
        public static final String ACCESS_CODE = "access_code";
        public static final String DEPARTMENT = "department";
        public static final String AD_ACCESS = "ad_access";

        public static String getTableName() {
            return TABLE_NAME;
        }

        static String getCreateSql() {
            return "CREATE TABLE "+TABLE_NAME+" ( " +
                _ID + " INTEGER PRIMARY KEY, " +
                ID + " VARCHAR(100) NOT NULL UNIQUE, " +
                NAME + " VARCHAR(100) NOT NULL, " +
                GENDER + " VARCHAR(6) NOT NULL, "  +
                EMAIL_ADDRESS + " VARCHAR(100) NOT NULL, " +
                ACCESS_CODE + " INTEGER NOT NULL, " +
                DEPARTMENT + " VARCHAR(100) NOT NULL, " +
                AD_ACCESS + " INTEGER NOT NULL" +
            ");";
        }
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return helper.getWritableDatabase().delete(Employee.getTableName(), selection, selectionArgs);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long id = helper.getWritableDatabase().insert(Employee.getTableName(), null, values);
        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public boolean onCreate() {
        helper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return helper.getWritableDatabase()
                .query(Employee.getTableName(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return helper.getWritableDatabase()
                .update(Employee.getTableName(), values, selection, selectionArgs);
    }


    private static final class MainDatabaseHelper extends SQLiteOpenHelper {

        MainDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
