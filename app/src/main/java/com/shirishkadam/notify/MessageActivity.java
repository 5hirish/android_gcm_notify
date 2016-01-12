package com.shirishkadam.notify;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter rvadpter;
    RecyclerView.LayoutManager rvlayoutmanager;
    ArrayList<MessageData> Message_Dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //Activity activity = MessageActivity.this;

        rv=(RecyclerView)findViewById(R.id.recycler_view);
        //rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);

        rvlayoutmanager = new LinearLayoutManager(this);
        rv.setLayoutManager(rvlayoutmanager);

        Message_Dataset = new ArrayList<MessageData>();

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        String[] col = {db.dbGCM_Message_Id,db.dbGCM_Message_Topic,db.dbGCM_Message_Title,db.dbGCM_Message_Message,db.dbGCM_Message_Time};

        Cursor cur =dbr.query(db.dbGCM_table,col,null,null,null,null,null);

        if(cur!=null){
            while (cur.moveToNext()){
                int id =cur.getInt(cur.getColumnIndex(db.dbGCM_Message_Id));
                String topic =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Topic));
                String title =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Title));
                String message =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Message));
                String time =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Time));

                Message_Dataset.add(new MessageData(id,topic,title,message,time));

            }cur.close();
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
