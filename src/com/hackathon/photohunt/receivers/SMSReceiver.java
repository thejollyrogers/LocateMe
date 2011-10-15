package com.hackathon.photohunt.receivers;

import com.hackathon.photohunt.GlobalConstants;
import com.hackathon.photohunt.asynctasks.CreateNotificationTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

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
            boolean condition = firstMsg.startsWith(GlobalConstants.SMS_APP_IDENTIFIER);
            if (condition) {
            	this.abortBroadcast();
                for (int i=0; i<msgs.length; i++){
                    msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
//                    str += "SMS from " + msgs[i].getOriginatingAddress();                     
//                    str += " :";
                    str += msgs[i].getMessageBody().toString();
//                    str += "\n";
                }
            	CreateNotificationTask c = new CreateNotificationTask(context, str);
            	c.execute();
            }

            //---display the new SMS message---
//            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }        
	}

}
