package com.hackathon.photohunt.utility;

import com.hackathon.photohunt.GlobalConstants;

import android.telephony.SmsManager;

public class SmsUtility
{
	public static void sendLocationText(String phoneNumber, String location)
	{
		SmsManager manager = SmsManager.getDefault();
		String message = GlobalConstants.SMS_APP_IDENTIFIER +
				"\n" + phoneNumber +
				"\n" + location;
		manager.sendTextMessage(phoneNumber, null, message, null, null);
	}
}
