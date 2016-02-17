package com.shirishkadam.notify;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView mtitle,mmessage,mtopic,mtime;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Message");

        Intent in = getIntent();
        int messageId = in.getIntExtra("MessageId", 0);


        mtitle = (TextView)findViewById(R.id.mtitle);
        mtime = (TextView)findViewById(R.id.mtime);
        mmessage = (TextView)findViewById(R.id.mdetail);
        mtopic = (TextView)findViewById(R.id.mtopic);


        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        String[] col = {db.dbGCM_Message_Id,db.dbGCM_Message_Topic,db.dbGCM_Message_Title,db.dbGCM_Message_Message,db.dbGCM_Message_Time};

        String where = db.dbGCM_Message_Id+" = "+messageId+"";

        Cursor cur =dbr.query(db.dbGCM_table, col, where, null, null, null, null);

        if(cur!=null){
            while (cur.moveToNext()){
                id =cur.getInt(cur.getColumnIndex(db.dbGCM_Message_Id));
                String topic =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Topic));
                String title =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Title));
                String message =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Message));
                String time =cur.getString(cur.getColumnIndex(db.dbGCM_Message_Time));

                mtitle.setText(title);
                mmessage.setText(message);
                mtopic.setText(topic);
                mtime.setText(time);

            }cur.close();
        }

        dbr.close();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int menu_id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (menu_id){
            case android.R.id.home:
                finish();
                break;

            case R.id.action_about:

                Intent in = new Intent(this,AboutActivity.class);
                startActivity(in);

                break;
            case R.id.action_delete:

                SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                SQLiteDatabase dbw = db.getWritableDatabase();

                dbw.delete(db.dbGCM_table,db.dbGCM_Message_Id+"="+id,null);

                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
