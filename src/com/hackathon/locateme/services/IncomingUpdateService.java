package com.hackathon.locateme.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.hackathon.locateme.utility.LocationUtility;
import com.hackathon.locateme.utility.SharedPreferencesUtility;
import com.hackathon.locateme.utility.SmsUtility;

public class IncomingUpdateService extends Service {
	private static final String TAG = IncomingUpdateService.class.getName();
	
	public LocationUtility mLocUtil;
	public String mDestLocation;
	public double[] mDestCoordinates;
	public String mDestPhoneNumber;
	public String mName;
	public int m_count;
	private SharedPreferencesUtility mPrefs;

	@Override
	public void onCreate() {

	}

	@Override
	public int onStartCommand(Intent intent, int i1, int i2) 
	{
		mPrefs = new SharedPreferencesUtility(this);
		mDestLocation = mPrefs.getDestinationLocation();
		mDestCoordinates = LocationUtility.convertStringToLatLong(mDestLocation);
		mDestPhoneNumber = mPrefs.getDestinationPhoneNumber();

		mLocUtil = new LocationUtility(this);
		mLocUtil.setLocationListener(createNewLocationListener(this), 1000 * 20);

		m_count = 0;
		return Service.START_STICKY;
	}
    
	public boolean inProximity(double startLat, double startLong, double endLat, double endLong)
	{
		float[] results = new float[3];
		Location.distanceBetween(startLat, startLong,
				endLat, endLong, results);
		return results[0] <= 10 ? true : false;
	}

    public LocationListener createNewLocationListener(final Service context)
	{
		return new LocationListener() 
		{
		    public void onLocationChanged(Location location) 
		    {
		    	boolean inProximity = inProximity(mDestCoordinates[0], mDestCoordinates[1], 
		    			location.getLatitude(), location.getLongitude());
		    	if (m_count > 10 || !mPrefs.okayToSendLocation() || inProximity) 
		    	{
		    		Log.e("important", "about to stop service");
		    		System.runFinalizersOnExit(true);
		    		System.exit(0);
		    	}
		    	m_count++;

		    	Log.e(TAG, "Location changed, sending text.");

		    	String eta = "-1";
		    	String loc = LocationUtility.convertLatLongToString(location.getLatitude(), location.getLongitude());
		    	TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
		        String myNumber = mTelephonyMgr.getLine1Number();
		        Log.d("important", "WE are about to be sending location update text!");

		        SmsUtility.sendLocationUpdateText(mDestPhoneNumber, myNumber, loc, eta);
		    	Log.e("important", "Value of m_count: " + m_count);

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
	
    public SharedPreferences getSharedPrefs()
    {
    	return PreferenceManager.getDefaultSharedPreferences(this);
    }
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
