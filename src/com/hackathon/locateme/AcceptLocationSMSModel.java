package com.hackathon.locateme;

import com.hackathon.locateme.utility.LocationUtility;
import com.hackathon.locateme.utility.SharedPreferencesUtility;
import com.hackathon.locateme.utility.SmsUtility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AcceptLocationSMSModel
{
	private static final String TAG = AcceptLocationSMSModel.class.getName();
	
	private Activity mActivity;
	private Button mAcceptAndAllow;
	private Button mAcceptOnly;
	private Button mDecline;
	private TextView mAcceptText;
	private String mDestNumber;
	private String mMyNumber;
	private String mLocationString;

	private double[] mLocation;
	private SharedPreferencesUtility mPrefs;

	
	public AcceptLocationSMSModel(Activity activity, String phoneNumber, double[] location)
	{
		mActivity = activity;
		mDestNumber = phoneNumber;
		mLocation = location;
		mLocationString = LocationUtility.convertLatLongToString(location[0], location[1]);
		mPrefs = new SharedPreferencesUtility(mActivity);
		
		TelephonyManager mTelephonyMgr = 
				(TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
		mMyNumber = mTelephonyMgr.getLine1Number();
		
		attachViewsToActivity();
	}
	
	public void attachViewsToActivity()
	{
		mAcceptText = (TextView) mActivity.findViewById(R.id.accept_sms_text_view);
		mAcceptText.setText("Phone number " + mDestNumber + " has sent you their location." +
				" Would you like to accept and allow them to view your location?");
		mAcceptAndAllow = (Button) mActivity.findViewById(R.id.accept_and_allow_button);
		mAcceptAndAllow.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Accepting the location and allowing post back data");
				mPrefs.setDestinationLocation(mLocationString);
				mPrefs.setDestinationPhoneNumber(mDestNumber);
				mPrefs.setOkayToSendLocation(true);

				SmsUtility.sendAcceptedLocationtext(mDestNumber, mMyNumber);
				startServiceTask();
				
				Log.d(TAG, "Opening route guidance");
				String directions="google.navigation:q=" + mLocation[0] + "," + mLocation[1] + "&mode=w";
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				intent.setData(Uri.parse(directions));
				mActivity.startActivity(intent);
			}
			
		});
		mAcceptOnly = (Button) mActivity.findViewById(R.id.accept_only_button);
		mAcceptOnly.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Accepting location and rejecting post back data.");
				mPrefs.setDestinationLocation(mLocationString);
				mPrefs.setDestinationPhoneNumber(mDestNumber);
				mPrefs.setOkayToSendLocation(true);
				
				SmsUtility.sendAcceptedLocationtext(mDestNumber, mMyNumber);
				startServiceTask();

				Log.d(TAG, "Opening route guidance");
				String directions="google.navigation:q=" + mLocation[0] + "," + mLocation[1] + "&mode=w";
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				intent.setData(Uri.parse(directions));
				mActivity.startActivity(intent);
			}

		});
		mDecline = (Button) mActivity.findViewById(R.id.decline_button);
		mDecline.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Declining location, sending decline text and home activity");

				SmsUtility.sendDeclinedLocationText(mDestNumber,mMyNumber);
				mActivity.startActivity( new Intent(mActivity, HomeActivity.class));
			}
			
		});
	}
	
	public void startServiceTask()
	{
		StartServiceTask serviceTask = new StartServiceTask();
		serviceTask.execute();
	}
	
	public void releaseViewsFromActivity()
	{
		mAcceptAndAllow = null;
		mAcceptOnly = null;
		mDecline = null;
		
		mActivity = null;
	}
	
	public void resetActivity(Activity activity)
	{
		mActivity = activity;
	}
	
	public class StartServiceTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			Intent intent = new Intent();
			intent.setAction("com.hackathon.locateme.services.IncomingUpdateService");
			mActivity.startService(intent);
			return null;
		}
	}
}
