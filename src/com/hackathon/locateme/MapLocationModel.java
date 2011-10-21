package com.hackathon.locateme;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.hackathon.locateme.utility.LocationUtility;
import com.hackathon.photohunt.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MapLocationModel implements Closeable {
	private static final double FACTOR = 1E6;
	
	private MapActivity mActivity;
	private MapView mMapView;
	private MapController mMapController;
	private TextView mTitleBar;
	private double[] mLocation;
	private String mOthersPhoneNumber;
	private String mOthersName;
	private String mTitleBarText;
	private LinearLayout mLinearLayout;
	private String mAction;
	 // the destination point when 'outgoing'
	private GeoPoint mDestPoint;
	// the person who is incoming (can be a list in the future for multiple incomings)
	private GeoPoint mIncomingPoint; 
	// your point right now
	private GeoPoint mMyCurrentPoint;
	private List<Overlay> mMapOverlays;
	private MapItemizedOverlay mItemizedOverlay;
	private LocationUtility mLocationUtility;
	
	public MapLocationModel(MapActivity activity, String action, Bundle extras)
	{
		mActivity = activity;
		mAction = action;
		
		mLocationUtility = new LocationUtility(mActivity);
		mLocation = extras.getDoubleArray(GlobalConstants.LOCATION_KEY);
		
		if(action.equals(GlobalConstants.OUTGOING))
		{
			mOthersPhoneNumber = extras.getString(GlobalConstants.PHONE_NUMBER_KEY);
			mTitleBarText = mOthersPhoneNumber;
		}
		else if(mAction.equals(GlobalConstants.INCOMING))
		{
			mOthersName = extras.getString(GlobalConstants.NAME);
			mTitleBarText = "Incoming Friends";
		}
		
		initialSetUp();
	}
	
	public void initialSetUp()
	{
		attachViewsToActivity();
		
		mMapOverlays = mMapView.getOverlays();
		Drawable drawable = mActivity.getResources().getDrawable(R.drawable.icon);
		mItemizedOverlay = new MapItemizedOverlay(drawable, mActivity);
		
		attachMapOverlays();
	}
	public void attachMapOverlays()
	{
		OverlayItem othersOverlayItem = null;
		OverlayItem myOverlayItem = null;
		if(mAction.equals(GlobalConstants.OUTGOING))
		{
			mMapController.setZoom(18);
			mDestPoint = new GeoPoint((int)(mLocation[0]*FACTOR), (int)(mLocation[1]*FACTOR));
			othersOverlayItem = new OverlayItem(mDestPoint, mOthersPhoneNumber + "'s location", "Go here!");
			mMapController.animateTo(mDestPoint);
		}
		else if(mAction.equals(GlobalConstants.INCOMING))
		{
			mMapController.setZoom(16);
			mIncomingPoint =  new GeoPoint((int)(mLocation[0]*FACTOR), (int)(mLocation[1]*FACTOR));
			othersOverlayItem = new OverlayItem(mIncomingPoint, mOthersName + "'s location", "");
			
			double[] myCoordinates = LocationUtility.convertStringToLatLong(getCurrentLocation());
			mMyCurrentPoint = new GeoPoint((int)(myCoordinates[0]*FACTOR), (int)(myCoordinates[1]*FACTOR));
			myOverlayItem = new OverlayItem(mMyCurrentPoint, "My location", "");
			mItemizedOverlay.addOverlay(myOverlayItem);
			mMapController.animateTo(mMyCurrentPoint);
		}
		mItemizedOverlay.addOverlay(othersOverlayItem);
		mMapOverlays.add(mItemizedOverlay);
	}
	
	public void attachViewsToActivity()
	{
		mMapView = (MapView) mActivity.findViewById(R.id.mapview);
		mMapView.setBuiltInZoomControls(true);
		mMapController = mMapView.getController();
		
		mTitleBar = (TextView) mActivity.findViewById(R.id.number_text);
		mTitleBar.setText(mTitleBarText);
	}
	
	public void reset(MapActivity activity)
	{
		mActivity = activity;
		attachViewsToActivity();
		mMapOverlays = mMapView.getOverlays();
		if(mAction.equals(GlobalConstants.OUTGOING))
		{
			
		}
		mMapOverlays.add(mItemizedOverlay);
	}
	
	public void releaseViewsFromActivity()
	{
		mTitleBar = null;
		mMapView = null;
		
		mActivity = null;
	}
	
	public String getCurrentLocation()
	{
		return mLocationUtility.getCurrentLocation();
	}
	
	public double[] getLocationArray()
	{
		return mLocation;
	}

	@Override
	public void close() throws IOException
	{
		releaseViewsFromActivity();
	}
}
