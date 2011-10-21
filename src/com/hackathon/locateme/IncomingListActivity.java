package com.hackathon.locateme;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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
	
	public void createEntry(String name, String phoneNumber, String location, String eta) {
		mDbHelper.createEntry(name, phoneNumber, location, eta);
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
        switch(item.getItemId()) {
        case 1:
        	mDbHelper.createEntry("Franklin", "1234567888", "47.658169,-122.303624", "");
        	mDbHelper.createEntry("Andrew", "1234567890", "47.658349,-122.318548", "");
            return true;
    }

    return super.onMenuItemSelected(featureId, item);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Cursor c = mDbHelper.fetchEntry(id);
    	String name = c.getString(2);
    	String location = c.getString(3);
    	
//    	AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//    	alertDialog.setTitle("Item Selected");
//    	alertDialog.setMessage(name + " " + location);
//    	alertDialog.show();
  
    	double[] coordinates = LocationUtility.convertStringToLatLong(location);
    	Intent intent = new Intent(this, MapLocationActivity.class);
    	intent.setAction(GlobalConstants.INCOMING);
    	intent.putExtra(GlobalConstants.LOCATION_KEY, coordinates);
    	intent.putExtra(GlobalConstants.NAME, name);
    	this.startActivity(intent);

    	super.onListItemClick(l, v, position, id);
    }
}
