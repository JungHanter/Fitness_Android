package com.wedo.fitdiary.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wedo.fitdiary.data.FitActivityType.Entry;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanter on 2014. 11. 28..
 */
public class FitActivityTypeManager extends SQLiteOpenHelper {
    private static String LOG_TAG = "FitActivityTypeManager";
    String[] ALL_COLUMNS = {
            Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM,
            Entry.COLUMN_NAME_ACTIVITY_NAME,
            Entry.COLUMN_NAME_MEASURE_TYPE
    };


    /*** CRUD Methods ***/
    static private long insertFitActivityType(SQLiteDatabase db, FitActivityType type) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        //values.put(Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM, type.getType());
        values.put(Entry.COLUMN_NAME_ACTIVITY_NAME, type.getName());
        values.put(Entry.COLUMN_NAME_MEASURE_TYPE, type.getMeasureType());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Entry.TABLE_NAME, null, values);
        type.setType((int)newRowId);

        return newRowId;
    }

    public long insertFitActivityType(FitActivityType type) {
        SQLiteDatabase db = this.getWritableDatabase();

        long newRowId = insertFitActivityType(db, type);
        type.setType((int)newRowId);

        db.close();
        return newRowId;
    }

    public boolean deleteFitActivityType(FitActivityType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deleteRowNum = db.delete(Entry.TABLE_NAME, Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM + " = ?",
                new String[] { String.valueOf(type.getType()) });

        db.close();
        return (deleteRowNum > 0);
    }

    public boolean updateFitActivityType(FitActivityType type) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Entry.COLUMN_NAME_ACTIVITY_NAME, type.getName());
        values.put(Entry.COLUMN_NAME_MEASURE_TYPE, type.getMeasureType());

        // Insert the new row, returning the primary key value of the new row
        long updateRowNum = db.update(Entry.TABLE_NAME, values,
                Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM + " = ?",
                new String[] { String.valueOf(type.getType()) });

        db.close();
        return (updateRowNum > 0);
    }

    public List<FitActivityType> getFitActivityTypes() {
        List<FitActivityType> typeList = new ArrayList<FitActivityType>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Select All Query
        String selectQuery = "SELECT * FROM " + Entry.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int[] columnIndex = getColumnIndex(cursor);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FitActivityType type = new FitActivityType(
                        cursor.getInt(columnIndex[0]),
                        cursor.getString(columnIndex[1]),
                        cursor.getInt(columnIndex[2])
                );
                typeList.add(type);
            } while (cursor.moveToNext());
        }

        db.close();
        return typeList;
    }

    /*** private Utility Methods ***/
    private static int[] getColumnIndex(Cursor cursor) {
        int[] columnIndex = {
                cursor.getColumnIndex(Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM),
                cursor.getColumnIndex(Entry.COLUMN_NAME_ACTIVITY_NAME),
                cursor.getColumnIndex(Entry.COLUMN_NAME_MEASURE_TYPE)
        };
        return columnIndex;
    }



    /*** DB Helper Methods ***/
    @Override
    public void onCreate(SQLiteDatabase db) {
        DBManager.createDataBase(db);
    }

    static void createTable(SQLiteDatabase db) {
        Log.d("FitActivityTypeManager", "create DB TABLE " + Entry.TABLE_NAME);

        String SQL_CREATE = "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                Entry.COLUMN_NAME_ACTIVITY_NAME + " TEXT NOT NULL, " +
                Entry.COLUMN_NAME_MEASURE_TYPE + " INTEGER NOT NULL " +
                ")";

        db.execSQL(SQL_CREATE);

        insertFitActivityType(db, new FitActivityType("Running", FitMeasure.MEASURE_TYPE_DISTANCE));
        insertFitActivityType(db, new FitActivityType("Situp", FitMeasure.MEASURE_TYPE_SETS_N_REPS));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(SQL_DELETE_ENTRIES);
        String SQL_DELETE = "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }

    public FitActivityTypeManager(Context context) {
        super(context, DBManager.DATABASE_NAME, null, DBManager.DATABSE_VERSION);
    }

    public FitActivityTypeManager(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DBManager.DATABASE_NAME, null, DBManager.DATABSE_VERSION, errorHandler);
    }

    /*public static FitActivityTypeManager loadFitActivityTypeManager(Context context) {
        FitActivityTypeManager manager = new FitActivityTypeManager(context);
        FitActivityType.setTypeList(manager.getFitActivityTypes());
        return manager;
    }*/
}
