package com.hackathon.locateme;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.hackathon.locateme.utility.IncomingDBAdapter;
import com.hackathon.locateme.utility.LocationUtility;

public class IncomingListActivity extends ListActivity {
	
	private IncomingDBAdapter mDbHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.incoming_list);
		mDbHelper = new IncomingDBAdapter(this);
		mDbHelper.open();
		fillData();
		registerForContextMenu(getListView());
	}
	
	public void fillData() {
		Cursor c = mDbHelper.fetchAllEntries();
		startManagingCursor(c);
		String[] from = new String[] { IncomingDBAdapter.KEY_NAME, IncomingDBAdapter.KEY_ETA };
        int[] to = new int[] { R.id.incoming_name, R.id.incoming_eta };
        
        SimpleCursorAdapter notes = 
        	new SimpleCursorAdapter(this, R.layout.incoming_list_item, c, from, to);
        setListAdapter(notes);
	}
	
	public void createEntry(String name, String phoneNumber, String location, String eta, String accepted) {
		mDbHelper.createEntry(name, phoneNumber, location, eta, accepted);
		fillData();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "test insert");
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) 
        {
        case 1:
        	mDbHelper.createEntry("Franklin", "1234567888", "47.658169,-122.303624", "", "true");
        	mDbHelper.createEntry("Andrew", "1234567890", "47.658349,-122.318548", "", "true");
            return true;
        
    }

    return super.onMenuItemSelected(featureId, item);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Cursor c = mDbHelper.fetchEntry(id);
    	String name = c.getString(2);
    	String location = c.getString(3);
    	String accepted = c.getString(c.getColumnIndex(IncomingDBAdapter.KEY_ACCEPTED));
    	if(accepted.equals("false"))
    	{
    		Toast t = Toast.makeText(this, name + " hasn't accepted your location yet.", Toast.LENGTH_SHORT);
    		t.show();
    		return;
    	}
    	else if(location == null)
    	{
    		Toast t = Toast.makeText(this, name + "'s location hasn't been established yet.", Toast.LENGTH_SHORT);
    	}

    	double[] coordinates = LocationUtility.convertStringToLatLong(location);
    	Intent intent = new Intent(this, MapLocationActivity.class);
    	intent.setAction(GlobalConstants.INCOMING);
    	intent.putExtra(GlobalConstants.LOCATION_KEY, coordinates);
    	intent.putExtra(GlobalConstants.NAME, name);
    	this.startActivity(intent);

    	super.onListItemClick(l, v, position, id);
    }
}
