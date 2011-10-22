package com.hackathon.locateme.asynctasks;

import com.hackathon.locateme.utility.IncomingDBAdapter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

public class ParseLocationUpdateTextTask extends
		AsyncTask<Void, Void, Void>
{
	private static final String TAG = ParseLocationUpdateTextTask.class.getName();

	private Context mContext;
	private String mEta;
	private String mPhoneNumber;
	private String mCoordinates;
	
	public ParseLocationUpdateTextTask(Context context, String data)
	{
		mContext = context;
		Log.d(TAG, data);
		String dataArray[] = data.split("\n"); // this might need to be \\n
		Log.d(TAG, "dataArray length: " + dataArray.length);
		mPhoneNumber = dataArray[1];
		mCoordinates = dataArray[2];
		mEta = dataArray[3];
	}
	
	@Override
	protected Void doInBackground(Void... params)
	{
		IncomingDBAdapter mDbHelper = new IncomingDBAdapter(mContext);
		mDbHelper.open();
		Cursor cur = mDbHelper.fetchEntry(mPhoneNumber);
		if(cur == null)
		{
			// do we want to create a new record? 
			Log.e(TAG, "Somehow the record for this incoming got deleted, create new record.");
			// don't have name data, just use phone number
			mDbHelper.createEntry(mPhoneNumber, mPhoneNumber, mCoordinates, mEta, "true");
		}
		else
		{
			mDbHelper.updateEntry(cur.getInt(0), mCoordinates, mEta, "true");
		}
		
		return null;
	}
	
	
}
