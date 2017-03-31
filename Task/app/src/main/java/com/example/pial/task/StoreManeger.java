package com.example.pial.task;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;



public class StoreManeger {
    private Context context;
    DBHelper dbHelper;

    public StoreManeger(Context context) {
        this.context = context;
        dbHelper=new DBHelper(context);
    }

    public long addContact(ArrayList<String> name,ArrayList<String> phone){
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();

        for (int i=0;i<name.size();i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.COLUMN_NAME, name.get(i));
            contentValues.put(DBHelper.COLUMN_PHONE, phone.get(i));
            sqLiteDatabase.insert(DBHelper.TABLE_CONTACT,null,contentValues);
        }



        long result=1;
        sqLiteDatabase.close();
        return result;

    }

    public ArrayList<Store> getAllContact(int id){
        ArrayList<Store>storage=new ArrayList<>();
        String selectQuery="select * from "+DBHelper.TABLE_CONTACT
                +" limit 0,"+id;
        SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                int ID=cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
                String name=cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));
                String phone=cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PHONE));



                Store store=new Store(ID,name,phone);

                storage.add(store);
            }while(cursor.moveToNext());
        }

        sqLiteDatabase.close();
        return storage;
    }
}
