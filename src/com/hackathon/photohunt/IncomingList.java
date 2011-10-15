package com.hackathon.photohunt;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.hackathon.photohunt.utility.IncomingDBAdapter;

public class IncomingList extends ListActivity {
	
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

        return super.onMenuItemSelected(featureId, item);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
      AlertDialog alertDialog = new AlertDialog.Builder(this).create();
      alertDialog.setTitle("Item Selected");
      alertDialog.setMessage("niggas");
      alertDialog.show();

      super.onListItemClick(l, v, position, id);
    }
}
