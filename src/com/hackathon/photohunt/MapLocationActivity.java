package com.hackathon.photohunt;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapLocationActivity extends MapActivity {

	private static final double FACTOR = 1E6;
	
	private LinearLayout mLinearLayout;
	private MapView mMapView;
	private MapController mMapController;
	private String mPhoneNumber;
	private double[] mLocation;
	private TextView mPhoneNumberTitle;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_location_layout);
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setBuiltInZoomControls(true);
		mMapController = mMapView.getController();
		String action = getIntent().getAction();
		
		if (action.equals(GlobalConstants.OUTGOING)) {
			mMapController.setZoom(18);
			
			Bundle extras = getIntent().getExtras();
			mPhoneNumber = extras.getString(GlobalConstants.PHONE_NUMBER_KEY);
			mLocation = extras.getDoubleArray(GlobalConstants.LOCATION_KEY);
			
			List<Overlay> mapOverlays = mMapView.getOverlays();
			Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
			HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable,this);
	//		GeoPoint point = new GeoPoint(30443769,-91158458);
	//		Log.d("FRANKLIN", "" + mLocation[0] + ", " + mLocation[1]);
			
			GeoPoint point = new GeoPoint((int)(mLocation[0]*FACTOR), (int)(mLocation[1]*FACTOR));
			OverlayItem overlayitem = new OverlayItem(point, mPhoneNumber + "'s location", "Go here!");
			
			mMapController.animateTo(point);
			
			itemizedoverlay.addOverlay(overlayitem);
			
			mapOverlays.add(itemizedoverlay);
		} else {
			
		}
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "Navigate to destination");
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case 1:
				String directions="google.navigation:q=" + mLocation[0] + "," + mLocation[1] + "&mode=w";
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				intent.setData(Uri.parse(directions));
				startActivity(intent);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }


}
