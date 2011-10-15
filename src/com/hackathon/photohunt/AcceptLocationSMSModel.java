package com.hackathon.photohunt;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
	private String mPhoneNumber;

	private double[] mLocation;
	private String mLocationString;
	private boolean mSendLocation;

	
	public AcceptLocationSMSModel(Activity activity, String phoneNumber, double[] location)
	{
		mActivity = activity;
		mPhoneNumber = phoneNumber;
		mLocation = location;

		attachViewsToActivity();
	}
	
	public void attachViewsToActivity()
	{
		mAcceptText = (TextView) mActivity.findViewById(R.id.accept_sms_text_view);
		mAcceptText.setText("Phone number " + mPhoneNumber + " has sent you their location." +
				" Would you like to accept and allow them to view your location?");
		mAcceptAndAllow = (Button) mActivity.findViewById(R.id.accept_and_allow_button);
		mAcceptAndAllow.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mSendLocation = true;
				
				startServiceTask();
				
//				Intent intent = new Intent(mActivity, MapLocationActivity.class);
//				intent.putExtra(GlobalConstants.PHONE_NUMBER_KEY, mPhoneNumber);
//				intent.putExtra(GlobalConstants.LOCATION_KEY, mLocation);
//				intent.putExtra(GlobalConstants.IS_LOCATION_UPDATING_KEY, true);
//				mActivity.startActivity(intent);
//				String directions="http://maps.google.com/maps?daddr="+mLocation[0]+","+mLocation[1];
				String directions="google.navigation:q=" + mLocation[0] + "," + mLocation[1] + "&mode=w";
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//				intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
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
				mSendLocation = false;
				
				startServiceTask();
				
//				Intent intent = new Intent(mActivity, MapLocationActivity.class);
//				intent.putExtra(GlobalConstants.PHONE_NUMBER_KEY, mPhoneNumber);
//				intent.putExtra(GlobalConstants.LOCATION_KEY, mLocation);
//				intent.putExtra(GlobalConstants.IS_LOCATION_UPDATING_KEY, false);
//				mActivity.startActivity(intent);
//				String directions="http://maps.google.com/maps?daddr="+mLocation[0]+","+mLocation[1];
				String directions="google.navigation:q=" + mLocation[0] + "," + mLocation[1] + "&mode=w";
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//				intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
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
				// progress dialouge to ban the user
			}
			
		});
	}
	
	public void startServiceTask()
	{
		StartServiceTask serviceTask = new StartServiceTask();
		String[] params = {mPhoneNumber, mLocationString};
		serviceTask.execute(params);
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
	
	public class StartServiceTask extends AsyncTask<String, Void, Void>
	{

		@Override
		protected Void doInBackground(String... params)
		{
			Intent intent = new Intent();
			intent.putExtra("phoneNumber", mPhoneNumber);
			intent.putExtra("location", mLocationString);
			mActivity.startService(intent);
			return null;
		}
		
	}
}
