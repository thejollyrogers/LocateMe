package com.hackathon.photohunt.utility;

import android.content.Context;
import android.location.LocationManager;

public class LocationUtility
{
	LocationManager m_manager;
	Location
	
	public LocationUtility(Context context)
	{
		m_manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	
	}
	
	public String getCurrentLocation()
	{
		
	}
	
}
