package com.hackathon.photohunt;

import android.app.Activity;
import android.os.Bundle;

public class HomeActivity extends Activity {
	
	private static final String TAG = HomeActivity.class.getName();
	private HomeModel m_model;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        
        m_model = (HomeModel) getLastNonConfigurationInstance();
        if(m_model != null)
        {
        	m_model.resetActivity(this);
        	m_model.attachViewsToActivity();
        }
        else
        {
        	m_model = new HomeModel(this);
        }
    }
    
    @Override
    public HomeModel onRetainNonConfigurationInstance() 
    {
        m_model.releaseViewsFromActivity();
        return m_model;
    }
}