package com.shirishkadam.notify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();

        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sf.getString("RegistrationID", "");

        TextView info = (TextView)findViewById(R.id.info);
        info.setText("Token: "+token);
        Button fork = (Button)findViewById(R.id.fork);
        fork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri url = Uri.parse("https://github.com/5hirish/android_gcm_notify");
                Intent in = new Intent(Intent.ACTION_VIEW,url);
                startActivity(in);
            }
        });

    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
