package com.hackathon.locateme.utility;

import com.hackathon.locateme.GlobalConstants;

import android.telephony.SmsManager;

public final class SmsUtility
{
	public static void sendLocationText(String sendNumber, String myNumber, String location, String name)
	{
		SmsManager manager = SmsManager.getDefault();
		String message = GlobalConstants.SMS_APP_IDENTIFIER +
				"\n" + myNumber +
				"\n" + location +
				"\n" + name;
		manager.sendTextMessage(sendNumber, null, message, null, null);
	}
	
	public static void sendLocationUpdateText(String sendNumber, String myNumber, String location, String eta)
	{
		SmsManager manager = SmsManager.getDefault();
		String message = GlobalConstants.SMS_APP_UPDATE_IDENTIFIER +
				"\n" + myNumber +
				"\n" + location +
				"\n" + eta;
		manager.sendTextMessage(sendNumber, null, message, null, null);
	}
	
	public static void sendDeclinedLocationText(String sendNumber, String myNumber)
	{
		SmsManager manager = SmsManager.getDefault();
		String message = GlobalConstants.SMS_APP_DECLINE_LOCATION +
				"\n" + myNumber;
		manager.sendTextMessage(sendNumber, null, message, null, null);
	}
	
	public static void sendAcceptedLocationtext(String sendNumber, String myNumber)
	{
		SmsManager manager = SmsManager.getDefault();
		String message = GlobalConstants.SMS_APP_ACCEPTED_LOCATION +
				"\n" + myNumber;
		manager.sendTextMessage(sendNumber, null, message, null, null);
	}
}
