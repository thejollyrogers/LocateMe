package com.hackathon.photohunt;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class HomeModel
{
	private static final String TAG = HomeModel.class.getName();
	
	private Activity m_activity;
	private RelativeLayout m_send;
	private RelativeLayout m_incoming;
	private RelativeLayout m_outgoing;
	
	public HomeModel(Activity activity)
	{
		m_activity = activity;
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
				// 
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
}
