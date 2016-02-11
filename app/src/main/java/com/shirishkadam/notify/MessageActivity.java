package com.shirishkadam.notify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmPubSub;

import java.io.IOException;
import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter rvadpter;
    RecyclerView.LayoutManager rvlayoutmanager;
    ArrayList<MessageData> Message_Dataset;
    String in_topic, selected_topic = "";

    public static final String Topic_FE = "/topics/FE";
    public static final String Topic_SE = "/topics/SE";
    public static final String Topic_TE = "/topics/TE";
    public static final String Topic_BE = "/topics/BE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent in = getIntent();
        in_topic = in.getStringExtra("Topic");
        //Activity activity = MessageActivity.this;

        switch (in_topic){
            case Topic_FE:
                selected_topic = "First Year";
                break;
            case Topic_SE:
                selected_topic = "Second Year";
                break;
            case Topic_TE:
                selected_topic = "Third Year";
                break;
            case Topic_BE:
                selected_topic = "Final Year";
                break;
        }

        setTitle(selected_topic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rv=(RecyclerView)findViewById(R.id.recycler_view);
        //rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);

        rvlayoutmanager = new LinearLayoutManager(this);
        rv.setLayoutManager(rvlayoutmanager);

        refresh_dataset();

        rvadpter = new MessageAdapter(getApplicationContext(),Message_Dataset);
        rv.setAdapter(rvadpter);

        rv.setItemAnimator(new DefaultItemAnimator());
    }

    void refresh_dataset(){
        Message_Dataset = new ArrayList<MessageData>();

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        String[] col = {db.dbGCM_Message_Id,db.dbGCM_Message_Topic,db.dbGCM_Message_Title,db.dbGCM_Message_Message,db.dbGCM_Message_Time};
        String where = db.dbGCM_Message_Topic+" = '"+selected_topic+"'";

        Cursor cur =dbr.query(db.dbGCM_table,col,where,null,null,null,db.dbGCM_Message_Id+" DESC");

        if(cur!=null){
            while (cur.moveToNext()){
                int id =cur.getInt(cur.getColumnIndex(db.dbGCM_Message_Id));
                String topic =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Topic));
                String title =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Title));
                String message =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Message));
                String time =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Time));

                Message_Dataset.add(new MessageData(id,topic,title,message,time));

            }cur.close();
        }else {
            Toast.makeText(getApplicationContext(),"No new messages!",Toast.LENGTH_LONG).show();
        }

        dbr.close();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int menu_id = item.getItemId();
        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sf.getString("RegistrationID", "");
        GcmPubSub pubSub = GcmPubSub.getInstance(this);


        //noinspection SimplifiableIfStatement
        switch (menu_id){
            case android.R.id.home:
                finish();
                break;

            case R.id.action_about:

                Intent in = new Intent(this,AboutActivity.class);
                startActivity(in);

                break;

            case R.id.action_unsubscribe:
                try {
                    pubSub.unsubscribe(token, in_topic);                                            //Unsubscribe from a topic
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"You are now Un-Subscribed from this topic..!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.action_subscribe:
                try {
                    pubSub.subscribe(token, in_topic, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"You are now Subscribed to this topic..!",Toast.LENGTH_SHORT).show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public class MessageData {
        int Id;
        String Topic;
        String Title;
        String Message;
        String Time;

        MessageData (int id, String topic, String title, String message, String time){
            Id = id;
            Topic = topic;
            Title = title;
            Message = message;
            Time = time;
        }
    }
}
