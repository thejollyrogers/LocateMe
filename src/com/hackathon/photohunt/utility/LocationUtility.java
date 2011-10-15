package com.hackathon.photohunt.utility;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

public class LocationUtility
{
	private final LocationListener m_locationListener;
	private final LocationManager m_manager;
	
	private Location m_location;
	
	public LocationUtility(Context context)
	{
		m_manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		m_locationListener = createNewLocationListener();
		m_location = m_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public String getCurrentLocation()
	{
		return convertLatLongToString(m_location.getLatitude(), m_location.getLongitude());
	}
	
	public static String convertLatLongToString(double lati, double longi)
	{
		String latitude = Location.convert(lati, Location.FORMAT_SECONDS);
		String longitude = Location.convert(longi, Location.FORMAT_SECONDS);
		return latitude + "," + longitude;
	}
	
	public void stopListening()
	{
		m_manager.removeUpdates(m_locationListener);
	}
	
	public LocationListener createNewLocationListener()
	{
		return new LocationListener() 
		{
		    public void onLocationChanged(Location location) {
		        // Called when a new location is found by the network location provider.
		        setLocation(location);
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
	
	public void setLocation(Location location)
	{
		m_location = location;
	}
}
