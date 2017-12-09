package com.hhcl.cropadvisory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Karthik Kumar K on 21-11-2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "cropAdvisory";

    // Contacts table name
    private static final String TABLE_CROP = "crop";

    // Contacts Table Columns names
    private static final String NOTIFICATION_ID = "notifyid";
    private static final String TITLE_NAME = "titlename";
    private static final String TITLE_MESSAGE = "title_message";
    private static final String IMAGE_URL = "image_url";
    private static final String IMAGE_TYPE = "image_type";
    private static final String IMAGE_FULL = "image_full";
    private static final String PAY_LOAD = "pay_load";
    private static final String TIME_STAMP = "time_stamp";
    private static final String STAR_STATUS = "star_status";
    private static final String DELETE_STATUS = "delete_status";
    // Delete Table Columns names


    private static final String TABLE_DELETE = "delet";

    // Delete Table Columns names
    private static final String DELNOTIFICATION_ID = "delnotifyid";
    private static final String DELTITLE_NAME = "deltitlename";
    private static final String DELTITLE_MESSAGE = "deltitle_message";
    private static final String DELIMAGE_URL = "delimage_url";
    private static final String DELIMAGE_TYPE = "delimage_type";
    private static final String DELIMAGE_FULL = "delimage_full";
    private static final String DELPAY_LOAD = "delpay_load";
    private static final String DELTIME_STAMP = "deltime_stamp";
    private static final String DELSTAR_STATUS = "delstar_status";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CROP_TABLE = "CREATE TABLE " + TABLE_CROP + "(" + NOTIFICATION_ID + " TEXT, " + TITLE_NAME + " TEXT,"
                + TITLE_MESSAGE + " TEXT ," + IMAGE_URL + " TEXT ," + IMAGE_TYPE + " TEXT ," + IMAGE_FULL + " TEXT ," + PAY_LOAD + " TEXT ," + TIME_STAMP + " TEXT ," + STAR_STATUS + " TEXT ," + DELETE_STATUS + " TEXT " + ")";

        db.execSQL(CREATE_CROP_TABLE);

//        String CREATE_DELETE_TABLE = "CREATE TABLE " + TABLE_DELETE + "(" + DELNOTIFICATION_ID + " INTEGER PRIMARY KEY, " + DELTITLE_NAME + " TEXT,"
//                + DELTITLE_MESSAGE + " TEXT ," + DELIMAGE_URL + " TEXT ," + DELIMAGE_TYPE + " TEXT ," + DELIMAGE_FULL + " TEXT ," + DELPAY_LOAD + " TEXT ," + DELTIME_STAMP + " TEXT ," + DELSTAR_STATUS + " TEXT " + ")";
//
//        db.execSQL(CREATE_DELETE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELETE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CROP);

// Create tables again
        onCreate(db);
    }

    // Adding new notification
    void addContact(DBModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE_NAME, model.getTt_name()); // Contact Name
        values.put(TITLE_MESSAGE, model.getMsg()); // Contact Phone
        values.put(IMAGE_URL, model.getUrl());
        values.put(IMAGE_TYPE, model.getImgtype());
        values.put(IMAGE_FULL, model.getImgfull());
        values.put(PAY_LOAD, model.getPayload());
        values.put(TIME_STAMP, model.getTstamp());
        values.put(STAR_STATUS, "0");
        values.put(DELETE_STATUS, "0");
        // Inserting Row
        db.insert(TABLE_CROP, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    DBModel getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CROP, new String[]{NOTIFICATION_ID,
                        TITLE_NAME, TITLE_MESSAGE, IMAGE_URL, IMAGE_TYPE, IMAGE_FULL, PAY_LOAD, TIME_STAMP, STAR_STATUS}, NOTIFICATION_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DBModel contact = new DBModel(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)
                , cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public ArrayList<DBModel> getAllContacts() {
        ArrayList<DBModel> contactList = new ArrayList<DBModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CROP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
//Log.d("Column8",cursor.getString(8));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DBModel contact = new DBModel();
                contact.setNo_id(cursor.getString(0));
                contact.setTt_name(cursor.getString(1));
                contact.setMsg(cursor.getString(2));
                contact.setUrl(cursor.getString(3));
                contact.setImgtype(cursor.getString(4));
                contact.setImgfull(cursor.getString(5));
                contact.setPayload(cursor.getString(6));
                contact.setTstamp(cursor.getString(7));
                contact.setStar(cursor.getString(8));
                contact.setDeletestatus(cursor.getString(9));
                // Adding contact to list
                contactList.add(0, contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(TITLE_NAME, contact.getTt_name());
//        values.put(TITLE_MESSAGE, contact.getMsg());
//        values.put(IMAGE_URL, contact.getUrl());
//        values.put(IMAGE_TYPE, contact.getImgtype());
//        values.put(IMAGE_FULL, contact.getImgfull());
//        values.put(PAY_LOAD, contact.getPayload());
//        values.put(TIME_STAMP, contact.getTstamp());
//        values.put(STAR_STATUS, contact.getStar());
        values.put(DELETE_STATUS, "1");
        // updating row
        //  sd.execSQL("DELETE FROM " + TABLE_CROP + " WHERE " + IMAGE_FULL + "= '" + imgfull + "'");
        return db.update(TABLE_CROP, values, IMAGE_FULL + " = ?",
                new String[]{contact});
    }



    public void deleteContact(String imgfull) {
//        Log.d("Test ID",imgfull);
        SQLiteDatabase sd = this.getWritableDatabase();
//        sd.delete(TABLE_CROP, IMAGE_FULL + " = ?",
//                new String[] { imgfull});
        sd.execSQL("DELETE FROM " + TABLE_CROP + " WHERE " + IMAGE_FULL + "= '" + imgfull + "'");
        sd.close();
    }


    public int updateContact2(DBModel contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE_NAME, contact.getTt_name());
        values.put(TITLE_MESSAGE, contact.getMsg());
        values.put(IMAGE_URL, contact.getUrl());
        values.put(IMAGE_TYPE, contact.getImgtype());
        values.put(IMAGE_FULL, contact.getImgfull());
        values.put(PAY_LOAD, contact.getPayload());
        values.put(TIME_STAMP, contact.getTstamp());
        values.put(STAR_STATUS, contact.getStar());
        values.put(DELETE_STATUS, contact.getDeletestatus());
        // updating row
        return db.update(TABLE_CROP, values, IMAGE_FULL + " = ?",
                new String[]{String.valueOf(contact.getImgfull())});
    }


    //    }
    public ArrayList<DBModel> getAllContacts2() {
        ArrayList<DBModel> contactList = new ArrayList<DBModel>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_CROP;
        String selectWues = "SELECT * FROM " + TABLE_CROP + " WHERE " + STAR_STATUS + "=" + 1;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectWues, null);
//Log.d("Column8",cursor.getString(8));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DBModel contact = new DBModel();
                contact.setNo_id(cursor.getString(0));
                contact.setTt_name(cursor.getString(1));
                contact.setMsg(cursor.getString(2));
                contact.setUrl(cursor.getString(3));
                contact.setImgtype(cursor.getString(4));
                contact.setImgfull(cursor.getString(5));
                contact.setPayload(cursor.getString(6));
                contact.setTstamp(cursor.getString(7));
                contact.setStar(cursor.getString(8));
                contact.setDeletestatus(cursor.getString(9));
                // Adding contact to list
                contactList.add(0, contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public ArrayList<DBModel> getAllafterDelete() {
        ArrayList<DBModel> contactList = new ArrayList<DBModel>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_CROP;
        String selectWues = "SELECT * FROM " + TABLE_CROP + " WHERE " + DELETE_STATUS + "=" + 0;
try{
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectWues, null);
    int i = 0;
    if (cursor.moveToFirst()) {
        do {
            // HModel hdata=new HModel();
            DBModel contact = new DBModel();
            contact.setNo_id(cursor.getString(0));
            contact.setTt_name(cursor.getString(1));
            contact.setMsg(cursor.getString(2));
            contact.setUrl(cursor.getString(3));
            contact.setImgtype(cursor.getString(4));
            contact.setImgfull(cursor.getString(5));
            contact.setPayload(cursor.getString(6));
            contact.setTstamp(cursor.getString(7));
            contact.setStar(cursor.getString(8));
            contact.setDeletestatus(cursor.getString(9));

            // Adding contact to list
            contactList.add(0, contact);
        } while (cursor.moveToNext());
    }

}catch (Exception e){
    e.printStackTrace();
}


//Log.d("Column8",cursor.getString(8));
        // looping through all rows and adding to list

        // return contact list
        return contactList;
    }


    public ArrayList<DBModel> getAllDeleteitems() {
        ArrayList<DBModel> contactList = new ArrayList<DBModel>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_CROP;
        String selectWues = "SELECT * FROM " + TABLE_CROP + " WHERE " + DELETE_STATUS + "=" + 1;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectWues, null);
//Log.d("Column8",cursor.getString(8));
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DBModel contact = new DBModel();
                contact.setNo_id(cursor.getString(0));
                contact.setTt_name(cursor.getString(1));
                contact.setMsg(cursor.getString(2));
                contact.setUrl(cursor.getString(3));
                contact.setImgtype(cursor.getString(4));
                contact.setImgfull(cursor.getString(5));
                contact.setPayload(cursor.getString(6));
                contact.setTstamp(cursor.getString(7));
                contact.setStar(cursor.getString(8));
                contact.setDeletestatus(cursor.getString(9));
                // Adding contact to list
                contactList.add(0, contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    public void deleteitem(String imgfull) {
        // Log.d("Test ID",imgfull);
        SQLiteDatabase sd = this.getWritableDatabase();
//        sd.delete(TABLE_CROP, IMAGE_FULL + " = ?",
//                new String[] { imgfull});
        sd.execSQL("DELETE FROM " + TABLE_DELETE + " WHERE " + DELIMAGE_FULL + "= '" + imgfull + "'");
        sd.close();
    }

}
