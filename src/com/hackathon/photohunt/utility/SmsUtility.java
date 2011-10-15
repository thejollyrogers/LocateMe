package com.hackathon.photohunt.utility;

import android.telephony.SmsManager;

public class SmsUtility
{
	public static void sendLocationText(String phoneNumber, String location)
	{
		SmsManager manager = SmsManager.getDefault();
		String message = "Sent using FindYoNigga: " +
				"\n" + phoneNumber +
				"\n" + location;
		manager.sendTextMessage(phoneNumber, null, message, null, null);
	}
}
