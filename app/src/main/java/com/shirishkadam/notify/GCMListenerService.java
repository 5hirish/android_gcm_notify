package com.shirishkadam.notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by felix on 10/1/16.
 */
public class GCMListenerService extends GcmListenerService {

    public static final String Topic_FE = "/topics/FE";
    public static final String Topic_SE = "/topics/SE";
    public static final String Topic_TE = "/topics/TE";
    public static final String Topic_BE = "/topics/BE";

    /**
     * Called when message is received.
     */

    @Override
    public void onMessageReceived(String from,Bundle data){
        String title = data.getString("title");
        String message = data.getString("message");
        String time = data.getString("time");

        if(from.startsWith(Topic_FE)){
            Log.d("Notify:GCMTopic", "Got message");
            String topic="First Year";

            sendNotification(title,message);

            insertMessage(topic,title,message,time);
        }else if(from.startsWith(Topic_SE)){
            Log.d("Notify:GCMTopic", "Got message");
            String topic="Second Year";

            sendNotification(title,message);

            insertMessage(topic,title,message,time);
        }else if(from.startsWith(Topic_TE)){
            Log.d("Notify:GCMTopic", "Got message");
            String topic="Third Year";

            sendNotification(title,message);

            insertMessage(topic,title,message,time);
        }else if(from.startsWith(Topic_BE)){
            Log.d("Notify:GCMTopic", "Got message");
            String topic="Final Year";

            sendNotification(title,message);

            insertMessage(topic,title,message,time);
        }

    }

    private void insertMessage(String topic, String title, String message, String time) {
        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbw = db.getWritableDatabase();

        ContentValues insert_msg = new ContentValues();
        insert_msg.put(db.dbGCM_Message_Topic,topic);
        insert_msg.put(db.dbGCM_Message_Title,title);
        insert_msg.put(db.dbGCM_Message_Message,message);
        insert_msg.put(db.dbGCM_Message_Time,time);

        long res = dbw.insert(db.dbGCM_table,null,insert_msg);

        dbw.close();
    }

    private void sendNotification(String title, String message) {
        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);
        int id = sf.getInt("Notification_Id",0);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id , intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id , notificationBuilder.build());

        id = id + 1;
        sf.edit().putInt("Notification_Id",id).apply();

    }
}
