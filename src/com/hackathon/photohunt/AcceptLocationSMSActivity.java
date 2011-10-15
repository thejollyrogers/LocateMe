package com.hackathon.photohunt;

import android.app.Activity;
import android.os.Bundle;

public class AcceptLocationSMSActivity extends Activity {
	
	private static final String TAG = AcceptLocationSMSActivity.class.getName();
	private AcceptLocationSMSModel mModel;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_location_sms_layout);
        
        Bundle extras = getIntent().getExtras();

        mModel = (AcceptLocationSMSModel) getLastNonConfigurationInstance();
        if(mModel != null)
        {
        	mModel.resetActivity(this);
        	mModel.attachViewsToActivity();
        }
        else
        {
        	String phoneNumber = extras.getString("phoneNumber");
        	String location = extras.getString("location");
        	mModel = new AcceptLocationSMSModel(this, phoneNumber, location);
        }
    }
    
    
    @Override
    public AcceptLocationSMSModel onRetainNonConfigurationInstance() 
    {
        mModel.releaseViewsFromActivity();
        return mModel;
    }

}
