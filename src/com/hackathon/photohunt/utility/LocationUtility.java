package com.hackathon.photohunt.utility;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;

public class LocationUtility
{
	private final LocationManager m_manager;
	
	private LocationProvider m_provider;
	private Location m_location;
	
	public LocationUtility(Context context)
	{
		m_manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		m_provider = LocationManager.NETWORK_PROVIDER;
	
	}
	
	public String getCurrentLocation()
	{
		
	}
	
	public void stopListening()
	{
		
	}
	
	public LocationListener 
}
