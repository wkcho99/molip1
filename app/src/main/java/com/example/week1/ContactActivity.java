package com.example.week1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Window;
import android.widget.TextView;

public class ContactActivity extends Activity {
    TextView name;
    TextView tel;
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contact_popup);
        name = (TextView) findViewById(R.id.name);
        tel = (TextView) findViewById(R.id.number);

        Intent intent = getIntent();
        String Name = intent.getStringExtra("name");
        String Phnumber = intent.getStringExtra("phnumber");
        name.setText(Name);
        tel.setText(Phnumber.substring(0,3) + "-" + Phnumber.substring(3,7) + "-" + Phnumber.substring(7,11));

    }
}