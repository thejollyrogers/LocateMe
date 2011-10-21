package com.hackathon.locateme;

import java.io.Closeable;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hackathon.locateme.utility.LocationUtility;
import com.hackathon.photohunt.R;

public class HomeModel implements Closeable
{
	private static final String TAG = HomeModel.class.getName();
	
	private LocationUtility m_locationUtility;
	
	private Activity m_activity;
	private RelativeLayout m_send;
	private RelativeLayout m_incoming;
	private RelativeLayout m_outgoing;
	
	public HomeModel(Activity activity)
	{
		m_activity = activity;
		m_locationUtility = new LocationUtility(m_activity);
		attachViewsToActivity();
	}
	
	public void attachViewsToActivity()
	{
		m_send = (RelativeLayout) m_activity.findViewById(R.id.send_button);
		m_send.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				m_activity.startActivityForResult(intent, HomeActivity.CONTACT_RESULT);
			}

		});
		m_incoming = (RelativeLayout) m_activity.findViewById(R.id.incoming_button);
		m_incoming.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(m_activity, IncomingListActivity.class);
				m_activity.startActivity(intent);
			}
			
		});
		m_outgoing = (RelativeLayout) m_activity.findViewById(R.id.outgoing_button);
		m_outgoing.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(m_activity);
				//if (prefs != null) {
					// String location = prefs.getString(GlobalConstants.SHARED_PREF_DESTINATION_KEY, null);
					// String phoneNumber = prefs.getString(GlobalConstants.SHARED_PREF_PHONE_NUMBER_KEY, null);
					String location = getCurrentLocation();
					String phoneNumber = "206-465-1374";
					if (location != null) {
						double[] coordinates = LocationUtility.convertStringToLatLong(location);
						Intent intent = new Intent(m_activity, MapLocationActivity.class);
						intent.setAction(GlobalConstants.OUTGOING);
						intent.putExtra(GlobalConstants.LOCATION_KEY, coordinates);
						intent.putExtra(GlobalConstants.PHONE_NUMBER_KEY, phoneNumber);
						m_activity.startActivity(intent);
					} else {
						createErrorToast(R.string.no_outgoing);
					}
				//} else {
				//	createErrorToast(R.string.no_outgoing);
				//}
			}
			
		});
	}
	
	public void releaseViewsFromActivity()
	{
		m_send = null;
		m_incoming = null;
		m_outgoing = null;
	}
	
	public void resetActivity(Activity activity)
	{
		m_activity = activity;
	}
	
	/**
	 * Takes a string id and prints as an error
	 * @param errorCode the int corresponding the error string's id
	 */
	public void createErrorToast(int errorCode)
	{
		Toast toast = Toast.makeText(m_activity, m_activity.getString(errorCode), Toast.LENGTH_SHORT);
		toast.show();
	}

	public String getCurrentLocation()
	{
		return m_locationUtility.getCurrentLocation();
	}

	@Override
	public void close() throws IOException
	{
		m_locationUtility.stopListening();
		m_locationUtility = null;
		releaseViewsFromActivity();
	}
}
