package com.shirishkadam.notify;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter rvadpter;
    RecyclerView.LayoutManager rvlayoutmanager;
    ArrayList<MessageData> Message_Dataset;

    public static final String Topic_FE = "/topics/FE";
    public static final String Topic_SE = "/topics/SE";
    public static final String Topic_TE = "/topics/TE";
    public static final String Topic_BE = "/topics/BE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent in = getIntent();
        String in_topic = in.getStringExtra("Topic"),selected_topic = "";
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

        rv=(RecyclerView)findViewById(R.id.recycler_view);
        //rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);

        rvlayoutmanager = new LinearLayoutManager(this);
        rv.setLayoutManager(rvlayoutmanager);

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

        rvadpter = new MessageAdapter(getApplicationContext(),Message_Dataset);
        rv.setAdapter(rvadpter);

        rv.setItemAnimator(new DefaultItemAnimator());
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
