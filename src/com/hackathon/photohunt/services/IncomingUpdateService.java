package com.hackathon.photohunt.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.hackathon.photohunt.utility.LocationUtility;
import com.hackathon.photohunt.utility.SmsUtility;

public class IncomingUpdateService extends Service {
	
	public LocationUtility m_locUtil;
	public String m_destPhoneNumber;
	
	@Override
	public void onCreate() {

	}
	
	@Override
	public int onStartCommand(Intent intent, int i1, int i2) {
		Bundle extras = intent.getExtras();
		m_destPhoneNumber = extras.getString("phoneNumber");
		String destinationLocation = extras.getString("location");
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor prefEdit = prefs.edit();
		prefEdit.putString("destination", destinationLocation);
		prefEdit.commit();
		
		m_locUtil = new LocationUtility(this);
		m_locUtil.setLocationListener(createNewLocationListener(), 1000 * 5);
		return Service.START_STICKY;
	}
    
    public LocationListener createNewLocationListener()
	{
		return new LocationListener() 
		{
		    public void onLocationChanged(Location location) {
		    	String eta = "-1";
		    	String loc = LocationUtility.convertLatLongToString(location.getLatitude(), location.getLongitude());
		    	SmsUtility.sendLocationUpdateText(m_destPhoneNumber, loc, eta);
		    }

		    public void onProviderEnabled(String provider) 
		    {
		    	
		    }

		    public void onProviderDisabled(String provider) 
		    {
		    	
		    }

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras)
			{
				
			}
		};
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
