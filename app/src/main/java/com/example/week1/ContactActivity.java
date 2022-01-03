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
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;

public class ContactActivity extends Activity {
    TextView name;
    TextView tel;
    TextView id;
    int[] images = new int[] {R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4, R.drawable.profile5, R.drawable.profile6, R.drawable.profile7};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contact_popup);
        //ImageView mImageView = (ImageView)findViewById(R.id.profile);
        int imageId = (int)(Math.random() * images.length);
        //mImageView.setBackgroundResource(images[imageId]);
        name = (TextView) findViewById(R.id.name);
        tel = (TextView) findViewById(R.id.number);
        Intent intent = getIntent();
        String Name = intent.getStringExtra("name");
        String Phnumber = intent.getStringExtra("phnumber");
        name.setText(Name);
        if(Phnumber.length()==11) tel.setText(Phnumber.substring(0,3) + "-" + Phnumber.substring(3,7) + "-" + Phnumber.substring(7,11));
        else if(Phnumber.length()==10) tel.setText(Phnumber.substring(0,3) + "-" + Phnumber.substring(3,6) + "-" + Phnumber.substring(6,10));
        else if(Phnumber.length()==9) tel.setText(Phnumber.substring(0,2) + "-" + Phnumber.substring(2,5) + "-" + Phnumber.substring(5,9));
        else tel.setText(Phnumber);
        Button b = findViewById(R.id.button2);
        Button b3 = findViewById(R.id.button3);
        Button b4 = findViewById(R.id.button4);
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
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Sharing_intent = new Intent(Intent.ACTION_SEND);
                Sharing_intent.setType("text/plain");

                String Test_Message = "[이름]" + Name + "\n" + "[전화번호]" + Phnumber;

                Sharing_intent.putExtra(Intent.EXTRA_TEXT, Test_Message);

                Intent Sharing = Intent.createChooser(Sharing_intent, "공유하기");
                startActivity(Sharing);
            }
        });
    }
    }