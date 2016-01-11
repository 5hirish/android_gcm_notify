package com.shirishkadam.notify;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by felix on 11/1/16.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String dbName = "Notify_GCM";
    public static final String dbGCM_table = "GCM_Messages";
    public static final String dbGCM_Message_Id = "Id";
    public static final String dbGCM_Message_Topic = "Topic";
    public static final String dbGCM_Message_Message = "Message";
    public static final String dbGCM_Message_Title = "Title";
    public static final String dbGCM_Message_Time = "Time";
    public static final int db_version = 1;


    public SQLiteHelper(Context context) {
        super(context, dbName, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String creat_table = "CREATE TABLE "+dbGCM_table+"  ("+dbGCM_Message_Id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+dbGCM_Message_Topic+" TEXT, "+dbGCM_Message_Title+" TEXT, "+dbGCM_Message_Message+" TEXT, "+dbGCM_Message_Time+" TEXT)";
        sqLiteDatabase.execSQL(creat_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
