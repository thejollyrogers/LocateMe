package com.hackathon.locateme.receivers;

import com.hackathon.locateme.GlobalConstants;
import com.hackathon.locateme.asynctasks.CreateNotificationTask;
import com.hackathon.locateme.asynctasks.ParseLocationUpdateTextTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            // first message, do some pattern matching
            String firstMsg = SmsMessage.createFromPdu((byte[])pdus[0]).getMessageBody().toString();
            boolean condition = firstMsg.startsWith(GlobalConstants.SMS_APP_IDENTIFIER) || 
            		firstMsg.startsWith(GlobalConstants.SMS_APP_UPDATE_IDENTIFIER) ||
            		firstMsg.startsWith(GlobalConstants.SMS_APP_DECLINE_LOCATION) ||
            		firstMsg.startsWith(GlobalConstants.SMS_APP_ACCEPTED_LOCATION);
            if (condition) {
            	this.abortBroadcast();
                for (int i=0; i<msgs.length; i++){
                    msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
//                    str += "SMS from " + msgs[i].getOriginatingAddress();                     
//                    str += " :";
                    str += msgs[i].getMessageBody().toString();
//                    str += "\n";
                }
                if(firstMsg.startsWith(GlobalConstants.SMS_APP_IDENTIFIER))
                {
                	CreateNotificationTask c = new CreateNotificationTask(context, str, 
                			CreateNotificationTask.INCOMING_LOCATION);
                	c.execute();
                }
                else if(firstMsg.startsWith(GlobalConstants.SMS_APP_UPDATE_IDENTIFIER))
                {
                	ParseLocationUpdateTextTask task = new ParseLocationUpdateTextTask(context, str);
                	task.execute();
                }
                else if(firstMsg.startsWith(GlobalConstants.SMS_APP_DECLINE_LOCATION))
                {
                	CreateNotificationTask c = new CreateNotificationTask(context, str,
                			CreateNotificationTask.DECLINED_LOCATION);
                	c.execute();
                }
                else if(firstMsg.startsWith(GlobalConstants.SMS_APP_ACCEPTED_LOCATION))
                {
                	CreateNotificationTask c = new CreateNotificationTask(context, str,
                			CreateNotificationTask.ACCEPTED_LOCATION);
                	c.execute();
                }
            	
            }
        }        
	}

}
