package com.hackathon.photohunt;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AcceptLocationSMSModel
{
	private static final String TAG = AcceptLocationSMSModel.class.getName();
	
	private Activity mActivity;
	private Button mAcceptAndAllow;
	private Button mAcceptOnly;
	private Button mDecline;
	
	public AcceptLocationSMSModel(Activity activity)
	{
		mActivity = activity;
		attachViewsToActivity();
	}
	
	public void attachViewsToActivity()
	{
		mAcceptAndAllow = (Button) mActivity.findViewById(R.id.accept_and_allow_button);
		mAcceptAndAllow.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mActivity.startActivity(new Intent(mActivity, MapLocationActivity.class));
			}
			
		});
		mAcceptOnly = (Button) mActivity.findViewById(R.id.accept_only_button);
		mAcceptOnly.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mActivity.startActivity(new Intent(mActivity, MapLocationActivity.class));
			}
			
		});
		mDecline = (Button) mActivity.findViewById(R.id.decline_button);
		mDecline.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// launch outgoing activity if they have an outgoing activity
			}
			
		});
	}
	
	public void releaseViewsFromActivity()
	{
		mAcceptAndAllow = null;
		mAcceptOnly = null;
		mDecline = null;
	}
	
	public void resetActivity(Activity activity)
	{
		mActivity = activity;
	}

}
