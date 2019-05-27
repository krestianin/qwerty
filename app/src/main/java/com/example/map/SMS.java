package com.example.map;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMS extends BroadcastReceiver {


    public static final String ACTION ="android.provider.Telephony.SMS_RECEIVED";
    private static final String SMS_SENDER="123456789";

    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null &&
                ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }
            // Gradle project sync failed. Please fix your project and try again.SMS Sender, example: 123456789
            String sms_from = messages[0].getDisplayOriginatingAddress();

            //Lets check if SMS sender is 123456789
            if (sms_from.equalsIgnoreCase(SMS_SENDER)) {
                StringBuilder bodyText = new StringBuilder();

                // If SMS has several parts, lets combine it :)
                for (int i = 0; i < messages.length; i++) {
                    bodyText.append(messages[i].getMessageBody());
                }
                //SMS Body
                String body = bodyText.toString();
                // Lets get SMS Code
                String code = body.replaceAll("[^0-9]", "");
                Toast.makeText(context, body, Toast.LENGTH_SHORT).show();
            }
        }
    }

    }

