package com.wedo.fitdiary.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

import com.wedo.fitdiary.data.FitActivity.Entry;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanter on 2014. 11. 25..
 */
public class FitActivityDataManager extends SQLiteOpenHelper {
    private static String LOG_TAG = "FitActivityDataManager";
    String[] ALL_COLUMNS = {
            Entry.COLUMN_NAME_INDEX,
            Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM,
            Entry.COLUMN_NAME_BEGIN_TIME,
            Entry.COLUMN_NAME_END_TIME,
            Entry.COLUMN_NAME_MEASURE_VALUE
    };


    /*** CRUD Methods ***/
    public long insertFitActivity(FitActivity act) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Entry.COLUMN_NAME_INDEX, act.getIndex());
        values.put(Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM, act.getActivityTypeNum());
        values.put(Entry.COLUMN_NAME_BEGIN_TIME, act.getBeginTime().getTime());
        values.put(Entry.COLUMN_NAME_END_TIME, act.getEndTime().getTime());
        values.put(Entry.COLUMN_NAME_MEASURE_VALUE, act.getMeasureValue());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Entry.TABLE_NAME, null, values);

        db.close();
        act.setIndex(newRowId);
        return newRowId;
    }

    public boolean deleteFitActivity(FitActivity act) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deleteRowNum = db.delete(Entry.TABLE_NAME, Entry.COLUMN_NAME_INDEX + " = ?",
                new String[] { String.valueOf(act.getIndex()) });

        db.close();
        return (deleteRowNum > 0);
    }

    public boolean updateFitActivity(FitActivity updatedAct) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM, updatedAct.getActivityTypeNum());
        values.put(Entry.COLUMN_NAME_BEGIN_TIME, updatedAct.getBeginTime().getTime());
        values.put(Entry.COLUMN_NAME_END_TIME, updatedAct.getEndTime().getTime());
        values.put(Entry.COLUMN_NAME_MEASURE_VALUE, updatedAct.getMeasureValue());

        // Insert the new row, returning the primary key value of the new row
        long updateRowNum = db.update(Entry.TABLE_NAME, values,
                Entry.COLUMN_NAME_INDEX + " = ?",
                new String[] { String.valueOf(updatedAct.getIndex()) });

        db.close();
        return (updateRowNum > 0);
    }

    public List<FitActivity> getAllFitActivities() {
        List<FitActivity> fitActList = new ArrayList<FitActivity>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Entry.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int[] columnIndex = getColumnIndex(cursor);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try {
                    FitActivity act = new FitActivity(
                            cursor.getInt(columnIndex[1]),
                            new Date(cursor.getLong(columnIndex[2])),
                            new Date(cursor.getLong(columnIndex[3])),
                            cursor.getString(columnIndex[4])
                    );
                    act.setIndex(cursor.getLong(columnIndex[0]));

                    fitActList.add(act);
                } catch (ParseException pe) {
                    Log.d(LOG_TAG, "Parsing error, Check to stored data");
                    pe.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        db.close();
        return fitActList;
    }

    public List<FitActivity> getFitActivitiesByBeginTime(Date beginTime, Date endTime) {
        long beginTheTime = beginTime.getTime();
        long endTheTime = endTime.getTime();

        List<FitActivity> fitActList = new ArrayList<FitActivity>();
        SQLiteDatabase db = this.getReadableDatabase();

        String whereCaluse = String.format("%s >= ? AND %s <= ?",
                Entry.COLUMN_NAME_BEGIN_TIME, Entry.COLUMN_NAME_BEGIN_TIME);
        String[] whereArgs = { String.valueOf(beginTheTime), String.valueOf(endTheTime) };
        String sortOrder = Entry.COLUMN_NAME_BEGIN_TIME + " ASC";

        //query(String table, String[] columns, String selection, String[] selectionArgs,
        //      String groupBy, String having, String orderBy/*, String limit*/)

        Cursor cursor = db.query(Entry.TABLE_NAME,      // Table Name
                                 ALL_COLUMNS,           // colum names
                                 whereCaluse,           // where sentence
                                 whereArgs,             // where ? parameter
                                 null, null,            // group by / having
                                 sortOrder);            // order by
        int[] columnIndex = getColumnIndex(cursor);

        if (cursor.moveToFirst()) {
            do {
                try {
                    FitActivity act = new FitActivity(
                            cursor.getInt(columnIndex[1]),
                            new Date(cursor.getLong(columnIndex[2])),
                            new Date(cursor.getLong(columnIndex[3])),
                            cursor.getString(columnIndex[4])
                    );
                    act.setIndex(cursor.getLong(columnIndex[0]));

                    fitActList.add(act);
                } catch (ParseException pe) {
                    Log.d(LOG_TAG, "Parsing error, Check to stored data");
                    pe.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        db.close();
        return fitActList;
    }

    /*** private Utility Methods ***/
    private static int[] getColumnIndex(Cursor cursor) {
        int[] columnIndex = {
                cursor.getColumnIndex(Entry.COLUMN_NAME_INDEX),
                cursor.getColumnIndex(Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM),
                cursor.getColumnIndex(Entry.COLUMN_NAME_BEGIN_TIME),
                cursor.getColumnIndex(Entry.COLUMN_NAME_END_TIME),
                cursor.getColumnIndex(Entry.COLUMN_NAME_MEASURE_VALUE)
        };
        return columnIndex;
    }


    /*** DB Helper Methods ***/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                Entry.COLUMN_NAME_INDEX + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                Entry.COLUMN_NAME_ACTIVITY_TYPE_NUM + " INTEGER NOT NULL, " +
                Entry.COLUMN_NAME_BEGIN_TIME + " INTEGER NOT NULL, " +
                Entry.COLUMN_NAME_END_TIME + " INTEGER NOT NULL, " +
                Entry.COLUMN_NAME_MEASURE_VALUE + " TEXT NOT NULL" +
                ")";

        db.execSQL(SQL_CREATE);
        //db.execSQL(SQL_CREATE_ENTRIES);
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

    public FitActivityDataManager(Context context) {
        super(context, DBManager.DATABASE_NAME, null, DBManager.DATABSE_VERSION);
    }

    public FitActivityDataManager(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DBManager.DATABASE_NAME, null, DBManager.DATABSE_VERSION, errorHandler);
    }
}
