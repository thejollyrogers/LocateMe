package com.hackathon.photohunt;

import java.io.IOException;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.maps.MapActivity;

public class MapLocationActivity extends MapActivity {

	private double[] mLocation;
	private MapLocationModel mModel;
	private boolean retainingNonConfigurationInstance;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_location_layout);
		
		String action = getIntent().getAction();
		Bundle extras = getIntent().getExtras();
		
		mModel = (MapLocationModel) getLastNonConfigurationInstance();
		if(mModel == null)
		{
			mModel = new MapLocationModel(this, action, extras);
		}
		else
		{
			mModel.reset(this);
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
            	double[] location = mModel.getLocationArray();
				String directions="google.navigation:q=" + location[0] + "," + location[1] + "&mode=w";
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				intent.setData(Uri.parse(directions));
				startActivity(intent);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
    
    @Override
    public MapLocationModel onRetainNonConfigurationInstance() 
    {
    	retainingNonConfigurationInstance = true;
        mModel.releaseViewsFromActivity();
        return mModel;
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	if(retainingNonConfigurationInstance)
    	{
    		mModel.releaseViewsFromActivity();
    	}
    	else
    	{
    		try
			{
				mModel.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }


}
