package com.hackathon.photohunt.utility;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationUtility
{
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	
	private LocationListener m_locationListener;
	private final LocationManager m_manager;
	
	private Location m_location;
	
	public LocationUtility(Context context)
	{
		m_manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		m_locationListener = createNewLocationListener();
		m_location = m_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		startListening();
	}
	
	/**
	 * Returns current location
	 * @return current location, null if none
	 */
	public String getCurrentLocation()
	{
		if(m_location != null)
		{
			return convertLatLongToString(m_location.getLatitude(), m_location.getLongitude());
		}
		else
		{
			return null;
		}
	}
	
	public static String convertLatLongToString(double lati, double longi)
	{
		String latitude = Location.convert(lati, Location.FORMAT_SECONDS);
		String longitude = Location.convert(longi, Location.FORMAT_SECONDS);
		return latitude + "," + longitude;
	}
	
	public void startListening()
	{
		m_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, m_locationListener);
	}
	
	public static double[] convertStringToLatLong(String str)
	{
		String[] strArray = str.split(",");
		double[] coordinates = new double[2];
		coordinates[0] = Double.parseDouble(strArray[0]);
		coordinates[1] = Double.parseDouble(strArray[1]);
		return coordinates;
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
		    	if(isBetterLocation(m_location, location))
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
	
	public void setLocationListener(LocationListener listener)
	{
		stopListening();
		m_locationListener = listener;
		startListening();
	}

	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
}
