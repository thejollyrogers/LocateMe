package com.hackathon.photohunt.asynctasks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.hackathon.photohunt.AcceptLocationSMSActivity;
import com.hackathon.photohunt.GlobalConstants;
import com.hackathon.photohunt.R;
import com.hackathon.photohunt.utility.LocationUtility;

public class CreateNotificationTask extends AsyncTask<Void, Void, Void> {
	
	private Context mContext;
	private String mName;
	private String mPhoneNumber;
	private double[] mCoordinates;
	
	public CreateNotificationTask(Context context, String data) {
		mContext = context;
		String dataArray[] = data.split("\n"); // this might need to be \\n
		mPhoneNumber = dataArray[0];
		mCoordinates = LocationUtility.convertStringToLatLong(dataArray[1]);
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.icon, mName + " sent a location", System.currentTimeMillis());
		PendingIntent intent = PendingIntent.getActivity(mContext, 0, new Intent(mContext, AcceptLocationSMSActivity.class), 0);
//		double[] coordinates = LocationUtility.convertStringToLatLong(mCoordinates);
		double[] coordinates = {31.12, 67.21};
		n.setLatestEventInfo(mContext, "Location data sent from " + mName, "Location coordinates are " 
				+ coordinates[0] + ", " + coordinates[1], intent);
		nm.notify(GlobalConstants.INITIAL_SENT_LOCATION, n);
		return null;
	}

}
