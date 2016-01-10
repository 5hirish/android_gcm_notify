package com.shirishkadam.notify;

import android.content.Intent;

/**
 * Created by felix on 10/1/16.
 */
public class InstanceIDListenerService extends com.google.android.gms.iid.InstanceIDListenerService {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */

    @Override
    public void onTokenRefresh(){
        Intent in = new Intent(this,RegistrationIntentService.class);
        startService(in);
    }
}
