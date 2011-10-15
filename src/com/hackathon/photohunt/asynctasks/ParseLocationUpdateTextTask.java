package com.hackathon.photohunt.asynctasks;

import com.hackathon.photohunt.utility.IncomingDBAdapter;
import com.hackathon.photohunt.utility.LocationUtility;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

public class ParseLocationUpdateTextTask extends
		AsyncTask<Void, Void, Void>
{
	private static final String TAG = ParseLocationUpdateTextTask.class.getName();

	private Context mContext;
	private String mName;
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
		mName = dataArray[3];
	}
	
	@Override
	protected Void doInBackground(Void... params)
	{
		IncomingDBAdapter mDbHelper = new IncomingDBAdapter(mContext);
		mDbHelper.open();
		Cursor cur = mDbHelper.fetchAllEntries();
		Log.d("important", "we are about to try the cursor");
		if(cur != null)
		{
				Log.d("important", "creating a new entry");
				mDbHelper.createEntry(mPhoneNumber, mName , mCoordinates, null);
		}
		else
		{
			Log.d("important", "creating a new entry");
			mDbHelper.createEntry(mPhoneNumber, mName , mCoordinates, null);
		}
		
		return null;
	}
	
	
}
