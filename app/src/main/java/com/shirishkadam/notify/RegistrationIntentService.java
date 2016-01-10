package com.shirishkadam.notify;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by felix on 10/1/16.
 */
public class RegistrationIntentService extends IntentService {

    private static final String NAME = "RegIntentService";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    public RegistrationIntentService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);

        try{

            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_senderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);

            Log.i("Notify:GCMToken",token);

            sendRegistrationToServer(token);

            sf.edit().putBoolean(SENT_TOKEN_TO_SERVER,true).apply();

        } catch (Exception e){

            Log.d("Notify:GCMToken", "Failed to complete token refresh", e);
            sf.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();

        }

        Intent registration_complete = new Intent(REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registration_complete);
    }

    private void sendRegistrationToServer(String token) {

    }
}
