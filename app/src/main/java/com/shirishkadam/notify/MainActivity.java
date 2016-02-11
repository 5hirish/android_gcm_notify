package com.shirishkadam.notify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver registration;
    private ProgressBar registration_bar;
    private TextView info;

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String Topic_FE = "/topics/FE";
    public static final String Topic_SE = "/topics/SE";
    public static final String Topic_TE = "/topics/TE";
    public static final String Topic_BE = "/topics/BE";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        info = (TextView) findViewById(R.id.info);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        //if(ni != null && ni.isConnected()){

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

        //}else {
            //info.setText(R.string.no_net);
        //}

        if(checkPlayServices()){
            Intent ing = new Intent(MainActivity.this, RegistrationIntentService.class);
            startService(ing);
        }

        final SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean sentToken = sf.getBoolean(SENT_TOKEN_TO_SERVER, false);

        if(sentToken) {

            Button fe = (Button) findViewById(R.id.fe);
            Button se = (Button) findViewById(R.id.se);
            Button te = (Button) findViewById(R.id.te);
            Button be = (Button) findViewById(R.id.be);

            final Intent in = new Intent(MainActivity.this,MessageActivity.class);

            fe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    in.putExtra("Topic", Topic_FE);
                    startActivity(in);
                }
            });

            se.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    in.putExtra("Topic", Topic_SE);
                    startActivity(in);
                }
            });

            te.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    in.putExtra("Topic", Topic_TE);
                    startActivity(in);
                }
            });

            be.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    in.putExtra("Topic",Topic_BE);
                    startActivity(in);
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "Please wait, Registering", Toast.LENGTH_LONG).show();
        }

        info = (TextView) findViewById(R.id.info);
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
