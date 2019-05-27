package com.example.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;



public class Screen extends AppCompatActivity {
    private BroadcastReceiver mPowerKeyReceiver = null;
    public static boolean wasScreenOn = true;
    protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_screen);
        final Chronometer myChronometer = (Chronometer)findViewById(R.id.chronometer);
        Button buttonStart = (Button)findViewById(R.id.buttonstart);
        Button buttonStop = (Button)findViewById(R.id.buttonstop);
        Button buttonReset = (Button)findViewById(R.id.buttonreset);
        final IntentFilter theFilter = new IntentFilter();
        /** System Defined Broadcast */
        theFilter.addAction(Intent.ACTION_SCREEN_ON);
        theFilter.addAction(Intent.ACTION_SCREEN_OFF);

        mPowerKeyReceiver = new BroadcastReceiver() {
            @Override
                       public void onReceive(Context context, Intent intent) {
                String strAction = intent.getAction();
 if (strAction.equals(Intent.ACTION_SCREEN_OFF)) { myChronometer.stop();
                    wasScreenOn=true;
                }
                if (strAction.equals(Intent.ACTION_SCREEN_ON)) {
                    Toast.makeText(context.getApplicationContext(),"ура", Toast.LENGTH_SHORT).show();
                    myChronometer.start();
                    wasScreenOn=false;
                }

            }

        };

        getApplicationContext().registerReceiver(mPowerKeyReceiver, theFilter);
    }
    @Override
    protected void onPause() {
        // when the screen is about to turn off
        if (wasScreenOn) {
            // this is the case when onPause() is called by the system due to a screen state change
            Log.e("MYAPP", "SCREEN TURNED OFF");
        } else {
            // this is when onPause() is called when the screen state has not changed
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // only when screen turns on
        if (!wasScreenOn) {
            // this is when onResume() is called due to a screen state change
            Log.e("MYAPP", "SCREEN TURNED ON");
        } else {
            // this is when onResume() is called when the screen state has not changed
        }
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
    }


//import android.os.Bundle;
//import android.os.SystemClock;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Chronometer;
//import android.widget.Toast;
//
//import com.example.map.R;
//
//public class Screen extends AppCompatActivity {
//
//   private Chronometer mChronometer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_screen);
//
//        Button start=findViewById(R.id.start);
//        Button reset=findViewById(R.id.stop);
//
//        Button stop=findViewById(R.id.reset);
//        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer chronometer) {
//                long elapsedMillis = SystemClock.elapsedRealtime()
//                        - mChronometer.getBase();
//
//                if (elapsedMillis > 5000) {
//                    String strElapsedMillis = "Прошло больше 5 секунд";
//                    Toast.makeText(getApplicationContext(),
//                            strElapsedMillis, Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//        });
//    }
//
//    public void onStartClick(View view) {
//        mChronometer.setBase(SystemClock.elapsedRealtime());
//        mChronometer.start();
//    }
//
//    public void onStopClick(View view) {
//        mChronometer.stop();
//    }
//
//    public void onResetClick(View view) {
//        mChronometer.setBase(SystemClock.elapsedRealtime());
//    }
//}
