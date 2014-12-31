package com.wedo.fitdiary.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hanter on 2014. 11. 25..
 */
public class DBManager {
    public static final int DATABSE_VERSION = 1;
    public static final String DATABASE_NAME = "FitDiary.db";

    static void createDataBase(SQLiteDatabase db) {
        FitActivityTypeManager.createTable(db);
        FitActivityDataManager.createTable(db);
    }
}
