package com.hackathon.locateme.utility;

import com.hackathon.locateme.GlobalConstants;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPreferencesUtility
{
	private final SharedPreferences mPrefs;
	private final Editor mEditor;
	
	public SharedPreferencesUtility(Context context)
	{
		mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		mEditor = mPrefs.edit();
	}
	
	public boolean okayToSendLocation()
	{
		return mPrefs.getBoolean(GlobalConstants.SHARED_PREF_SEND_LOCATION, false);
	}
	
	public void setOkayToSendLocation(boolean okay)
	{
		mEditor.putBoolean(GlobalConstants.SHARED_PREF_SEND_LOCATION, okay);
		mEditor.commit();
	}	
	
	public String getDestinationLocation()
	{
		return mPrefs.getString(GlobalConstants.SHARED_PREF_DESTINATION_KEY, null);
	}
	
	public void setDestinationLocation(String location)
	{
		mEditor.putString(GlobalConstants.SHARED_PREF_DESTINATION_KEY, location);
		mEditor.commit();
	}
	
	public String getDestinationPhoneNumber()
	{
		return mPrefs.getString(GlobalConstants.SHARED_PREF_PHONE_NUMBER_KEY, null);
	}
	
	public void setDestinationPhoneNumber(String phoneNumber)
	{
		mEditor.putString(GlobalConstants.SHARED_PREF_PHONE_NUMBER_KEY, phoneNumber);
		mEditor.commit();
	}

}
