package com.example.pial.task;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME="task_database";
    public static final int DATABASE_VERSION=1;

    public static final String TABLE_CONTACT="contact";

    public static final String COLUMN_ID="id";
    public static final String COLUMN_NAME="userName";
    public static final String COLUMN_PHONE="userPhone";

    private String CREATE_TABLE_CONTACT="create table "+TABLE_CONTACT+"( "+COLUMN_ID+
            " integer primary key autoincrement, "+COLUMN_NAME+" text,"+COLUMN_PHONE+" text);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist "+TABLE_CONTACT);
        onCreate(db);
    }
}
