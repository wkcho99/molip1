package com.example.week1;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

import java.io.InputStream;

public class ContactActivity extends Activity {
    TextView name;
    TextView tel;
    int[] images = new int[] {R.drawable.ball1, R.drawable.ball2, R.drawable.ball3, R.drawable.ball4, R.drawable.ball5, R.drawable.ball6, R.drawable.ball7, R.drawable.ball8, R.drawable.ball9, R.drawable.ball10, R.drawable.ball11};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contact_popup);
        ImageView mImageView = (ImageView)findViewById(R.id.profile);
        name = (TextView) findViewById(R.id.name);
        tel = (TextView) findViewById(R.id.number);
        Intent intent = getIntent();
        String Name = intent.getStringExtra("name");
        String Phnumber = intent.getStringExtra("phnumber");
        Long PersonId = intent.getLongExtra("personid",0);
        Long PhotoId = intent.getLongExtra("photo",0);
        name.setText(Name);
        if (loadContactPhoto(getContentResolver(),PersonId,PhotoId) != null) mImageView.setImageBitmap(loadContactPhoto(getContentResolver(),PersonId,PhotoId));

        if(Phnumber.length()==11) {
            tel.setText(Phnumber.substring(0,3) + "-" + Phnumber.substring(3,7) + "-" + Phnumber.substring(7,11));
        }
        else if(Phnumber.length()==10) {
            tel.setText(Phnumber.substring(0,3) + "-" + Phnumber.substring(3,6) + "-" + Phnumber.substring(6,10));
        }
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
    protected Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id){
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr,uri);
        if(input!= null)
            return resizingBitmap(BitmapFactory.decodeStream(input));
        else Log.d("PHOTO","fail load");
        byte[] photoBytes = null;
        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI,photo_id);
        Cursor c = cr.query(photoUri,new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO},null,null,null);
        try{
            if(c.moveToFirst()) photoBytes = c.getBlob(0);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            c.close();
        }
        if(photoBytes!=null) return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes,0,photoBytes.length));
        else Log.d("PHOTO","fail load2");
        return null;
    }
    public Bitmap resizingBitmap(Bitmap oBitmap)
    {
        if(oBitmap==null) return null;
        float width = oBitmap.getWidth();
        float height = oBitmap.getHeight();
        float resizing_size = 480;
        Bitmap rBitmap = null;
        if(width>resizing_size){
            float mWidth = (float) (width/100);
            float fScale = (float) (resizing_size/mWidth);
            width *= (fScale/100);
            height *= (fScale/100);
        }
        else if(height>resizing_size){
            float mHeight = (float) (height/100);
            float fScale = (float) (resizing_size/mHeight);
            width *=(fScale/100);
            height *=(fScale/100);
        }
        Log.d("rBitmap:",width+","+height);
        rBitmap = Bitmap.createScaledBitmap(oBitmap,(int)width,(int)height,true);
        return rBitmap;
    }
    }