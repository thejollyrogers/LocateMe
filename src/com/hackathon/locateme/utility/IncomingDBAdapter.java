package com.hackathon.locateme.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IncomingDBAdapter {

    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_ETA = "eta";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ACCEPTED = "accepted";
    
    private static final String TAG = "IncomingDBAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    private static final String DATABASE_CREATE =
        "create table incomingInfo (_id integer primary key autoincrement, "
        + "phoneNumber text, name text, location text, eta text, accepted text);";
    
	private static final String DATABASE_NAME = "incomingDB";
    private static final String DATABASE_TABLE = "incomingInfo";
    private static final int DATABASE_VERSION = 2;
    
    private final Context mCtx;
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
    	
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }
        
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    	
    }
    
    public IncomingDBAdapter(Context ctx) {
    	this.mCtx = ctx;
    }
    
    public IncomingDBAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        mDbHelper.close();
    }
    
    public long createEntry(String name, String phoneNumber, String location, String eta, String accepted) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PHONE_NUMBER, phoneNumber);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_LOCATION, location);
        initialValues.put(KEY_ETA, eta);
        initialValues.put(KEY_ACCEPTED, accepted);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
    
    public boolean deleteEntry(long rowid) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowid, null) > 0;
    }
    
    public Cursor fetchEntry(long rowid) throws SQLException {
        Cursor mCursor =
            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_PHONE_NUMBER,
                    KEY_NAME, KEY_LOCATION, KEY_ETA, KEY_ACCEPTED}, KEY_ROWID + "=" + rowid, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    /*
     * Implementing this method to easily get a row by phone number because 
     * i don't want to reimplement the primary key as a string, I want to go drink.
     */
    public Cursor fetchEntry(String phoneNumber) throws SQLException {
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_PHONE_NUMBER,
                        KEY_NAME, KEY_LOCATION, KEY_ETA, KEY_ACCEPTED}, KEY_PHONE_NUMBER + "=" + phoneNumber, null,
                        null, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;
        }
    
    public Cursor fetchAllEntries() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_PHONE_NUMBER, KEY_NAME,
                 KEY_LOCATION, KEY_ETA, KEY_ACCEPTED}, null, null, null, null, null);
    }
    
    public boolean updateEntry(long rowid, String location, String eta, String accepted) {
        ContentValues args = new ContentValues();
        args.put(KEY_LOCATION, location);
        args.put(KEY_ETA, eta);
        args.put(KEY_ACCEPTED, true);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowid, null) > 0;
    }
}
