package com.hackathon.locateme.asynctasks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.hackathon.locateme.AcceptLocationSMSActivity;
import com.hackathon.locateme.GlobalConstants;
import com.hackathon.locateme.R;
import com.hackathon.locateme.utility.LocationUtility;

public class CreateNotificationTask extends AsyncTask<Void, Void, Void> {
	
	private static final String TAG = CreateNotificationTask.class.getName();
	
	private Context mContext;
	private String mPhoneNumber;
	private String mName;
	private double[] mCoordinates;
	
	public CreateNotificationTask(Context context, String data) {
		mContext = context;
		Log.d(TAG, data);
		String dataArray[] = data.split("\n"); // this might need to be \\n
		Log.d(TAG, "dataArray length: " + dataArray.length);
		mPhoneNumber = dataArray[1];
		mCoordinates = LocationUtility.convertStringToLatLong(dataArray[2]);
		mName = dataArray[3];
		Log.d(TAG, "converted coordinates: " + mCoordinates[0] + ", " + mCoordinates[1]);
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.icon, mPhoneNumber + " sent a location", System.currentTimeMillis());
		n.flags |= Notification.FLAG_AUTO_CANCEL;
		Intent i = new Intent(mContext, AcceptLocationSMSActivity.class);
		i.putExtra(GlobalConstants.PHONE_NUMBER_KEY, mPhoneNumber);
		i.putExtra(GlobalConstants.LOCATION_KEY, mCoordinates);
		i.putExtra(GlobalConstants.NAME, mName);
		i.setAction("" + System.currentTimeMillis());
		PendingIntent intent = PendingIntent.getActivity(mContext, 0, i, 0);
		n.setLatestEventInfo(mContext, "Location data sent from " + mPhoneNumber, "Coordinates: " 
				+ mCoordinates[0] + ", " + mCoordinates[1], intent);
		nm.notify(GlobalConstants.INITIAL_SENT_LOCATION, n);
		return null;
	}

}
