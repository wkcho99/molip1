package com.example.week1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
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
        Button b = findViewById(R.id.button2);
        Button b3 = findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context c = v.getContext();
                Intent intent2 = new Intent(Intent.ACTION_DIAL);
                intent2.setData(Uri.parse("tel:"+Phnumber));
                startActivity(intent2);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context c = v.getContext();
                Intent intent3 = new Intent(Intent.ACTION_VIEW);
                intent3.setData(Uri.parse("smsto:"+Phnumber));
                startActivity(intent3);

            }
        });
    }
    }