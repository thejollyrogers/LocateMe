package com.hackathon.photohunt;

import com.hackathon.photohunt.utility.LocationUtility;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HomeModel
{
	private static final String TAG = HomeModel.class.getName();
	
	private final LocationUtility m_locationUtility;
	
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
				// launch incoming activity
			}
			
		});
		m_outgoing = (RelativeLayout) m_activity.findViewById(R.id.outgoing_button);
		m_outgoing.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// launch outgoing activity if they have an outgoing activity
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
}
