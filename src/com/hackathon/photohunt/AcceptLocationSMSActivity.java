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
        
        mModel = (AcceptLocationSMSModel) getLastNonConfigurationInstance();
        if(mModel != null)
        {
        	mModel.resetActivity(this);
        	mModel.attachViewsToActivity();
        }
        else
        {
        	mModel = new AcceptLocationSMSModel(this);
        }
    }
    
    
    @Override
    public AcceptLocationSMSModel onRetainNonConfigurationInstance() 
    {
        mModel.releaseViewsFromActivity();
        return mModel;
    }

}
