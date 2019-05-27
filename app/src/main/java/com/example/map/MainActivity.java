package com.example.map;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
 class sms extends BroadcastReceiver {


    public static final String ACTION ="android.provider.Telephony.SMS_RECEIVED";
    private static final String SMS_SENDER="Ваня";

    @SuppressLint("NewApi")
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null &&
                ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }
            // SMS Sender, example: 123456789
            String sms_from = messages[0].getDisplayOriginatingAddress();
            Intent newintent = new Intent(context, MainActivity.class);
            newintent.putExtra("sms",sms_from);
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
public class MainActivity extends ListActivity  {

    private BroadcastReceiver mPowerKeyReceiver = null;
    sms massage=new sms();
    public void registerBroadcastReceiver(View view) {
        this.registerReceiver(massage, new IntentFilter(
                "android.intent.action.TIME_TICK"));
        Toast.makeText(getApplicationContext(), "Приёмник включен",
                Toast.LENGTH_SHORT).show();
    }

    // Отменяем регистрацию
    public void unregisterBroadcastReceiver(View view) {
        this.unregisterReceiver(massage);

        Toast.makeText(getApplicationContext(), "Приёмник выключён", Toast.LENGTH_SHORT)
                .show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
        final Chronometer myChronometer=findViewById(R.id.chronometer);
        final IntentFilter theFilter = new IntentFilter();
        /** System Defined Broadcast */
        theFilter.addAction(Intent.ACTION_SCREEN_ON);
        theFilter.addAction(Intent.ACTION_SCREEN_OFF);
        final int[] stoppedMilliseconds = {0};


        mPowerKeyReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String strAction = intent.getAction();
                if (strAction.equals(Intent.ACTION_SCREEN_OFF)) {
                    String chronoText = myChronometer.getText().toString();
                    String array[] = chronoText.split(":");
                    if (array.length == 2) {
                        stoppedMilliseconds[0] = Integer.parseInt(array[0]) * 60 * 1000
                                + Integer.parseInt(array[1]) * 1000;
                    } else if (array.length == 3) {
                        stoppedMilliseconds[0] = Integer.parseInt(array[0]) * 60 * 60 * 1000
                                + Integer.parseInt(array[1]) * 60 * 1000
                                + Integer.parseInt(array[2]) * 1000;
                    }

                    myChronometer.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds[0]);
                    myChronometer.stop();

                }
                else if (strAction.equals(Intent.ACTION_SCREEN_ON)) {
                    String chronoText = myChronometer.getText().toString();
                    String array[] = chronoText.split(":");
                    if (array.length == 2) {
                        stoppedMilliseconds[0] = Integer.parseInt(array[0]) * 60 * 1000
                                + Integer.parseInt(array[1]) * 1000;
                    } else if (array.length == 3) {
                        stoppedMilliseconds[0] = Integer.parseInt(array[0]) * 60 * 60 * 1000
                                + Integer.parseInt(array[1]) * 60 * 1000
                                + Integer.parseInt(array[2]) * 1000;
                    }

                    myChronometer.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds[0]);
                    Toast.makeText(context.getApplicationContext(),"ура", Toast.LENGTH_SHORT).show();
                    myChronometer.start();

                }

            }

        };

        getApplicationContext().registerReceiver(mPowerKeyReceiver, theFilter);


        if (getIntent().getExtras() != null) {
                  String smska =getIntent().getExtras().getString("sms");
        Toast.makeText(this, smska, Toast.LENGTH_SHORT).show();}



        String[] myArr = { "экран","карта","смс" };
        ArrayAdapter<String> monthAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, myArr);
        setListAdapter(monthAdapter);
}


    protected void onDestroy() {
        super.onDestroy();
        int apiLevel = Build.VERSION.SDK_INT;

        if (apiLevel >= 7) {
            try {
                getApplicationContext().unregisterReceiver(mPowerKeyReceiver);
            } catch (IllegalArgumentException e) {
                mPowerKeyReceiver = null;
            }
        } else {
            getApplicationContext().unregisterReceiver(mPowerKeyReceiver);
            mPowerKeyReceiver = null;
        }
    }



protected void onListItemClick(ListView l, View v, int position, long id) {
    String choice = (String) l.getAdapter().getItem(position);
    switch (choice){

        case "экран":   Intent g = new Intent(MainActivity.this, Screen.class); startActivity(g);break;
        case "карта":   Intent t = new Intent(MainActivity.this, map.class); startActivity(t);break;
        case "смс":     ; break;
    }

}}