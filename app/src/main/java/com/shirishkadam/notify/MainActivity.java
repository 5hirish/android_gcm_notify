package com.shirishkadam.notify;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver registration;

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

        FloatingActionButton next = (FloatingActionButton) findViewById(R.id.next);

        final SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean fresh_install = sf.getBoolean("Fresh_Install", true);

        final ProgressDialog registration_bar = new ProgressDialog(MainActivity.this);
        //registration_bar.setCancelable(true);
        registration_bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        registration_bar.setMessage(getString(R.string.registering_message));

        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.token_error_message)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        builder.create();

        if((ni != null && ni.isConnected() && fresh_install) || ni != null && ni.isConnected()){

            registration_bar.show();
            registration = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    registration_bar.dismiss();

                    SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(context);
                    boolean sentToken = sf.getBoolean(SENT_TOKEN_TO_SERVER,false);

                    if(sentToken) {
                        Toast.makeText(getApplicationContext(),getString(R.string.gcm_send_message),Toast.LENGTH_SHORT).show();
                    } else {
                        builder.show();
                    }

                }

            };

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_next, new SelectFragment());
            ft.commit();

            sf.edit().putBoolean("Fresh_Install",false).apply();


        }else if(ni == null && fresh_install) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_next, new NoConnFragment());
            ft.commit();

            sf.edit().putBoolean("Fresh_Install",false).apply();

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        if(checkPlayServices()){
            Intent ing = new Intent(MainActivity.this, RegistrationIntentService.class);
            startService(ing);
        }


        boolean sentToken = sf.getBoolean(SENT_TOKEN_TO_SERVER, false);

        if(sentToken) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_next, new SelectFragment());
            ft.commit();

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String select = sf.getString("Last_Visited", "Select");
                    final Intent in = new Intent(MainActivity.this, MessageActivity.class);

                    switch (select) {

                        case Topic_FE:
                            in.putExtra("Topic", Topic_FE);
                            startActivity(in);
                            break;

                        case Topic_SE:
                            in.putExtra("Topic", Topic_SE);
                            startActivity(in);
                            break;

                        case Topic_TE:
                            in.putExtra("Topic", Topic_TE);
                            startActivity(in);
                            break;

                        case Topic_BE:
                            in.putExtra("Topic", Topic_BE);
                            startActivity(in);
                            break;

                        default:
                            Toast.makeText(getApplicationContext(), "Please Select", Toast.LENGTH_SHORT).show();

                    }
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "Please wait, Registering", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(registration != null){
            LocalBroadcastManager.getInstance(this).registerReceiver(registration,
                new IntentFilter(REGISTRATION_COMPLETE));}
    }

    @Override
    protected void onPause() {
        if(registration != null){
            LocalBroadcastManager.getInstance(this).unregisterReceiver(registration);}
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
