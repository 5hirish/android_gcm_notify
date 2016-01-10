package com.shirishkadam.notify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver registration;
    private ProgressBar registration_bar;
    private TextView info;

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registration_bar = (ProgressBar) findViewById(R.id.registration_Bar);
        registration = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                registration_bar.setVisibility(ProgressBar.GONE);
                SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sf.getBoolean(SENT_TOKEN_TO_SERVER,false);

                if(sentToken) {
                    info.setText(getString(R.string.gcm_send_message));
                } else {
                    info.setText(getString(R.string.token_error_message));
                }

            }

        };

        info = (TextView) findViewById(R.id.info);

        if(checkPlayServices()){
            Intent in = new Intent(this, RegistrationIntentService.class);
            startService(in);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(registration,
                new IntentFilter(REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registration);
        super.onPause();
    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int rescode = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if(rescode != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(rescode)){
                googleApiAvailability.getErrorDialog(this,rescode,PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                Log.i("Noify:GoogleAPI","This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
