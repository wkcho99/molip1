package com.example.week1.ui.main;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week1.PreferenceManager;
import com.example.week1.R;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tab3Activity extends Fragment implements SensorEventListener {
    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    TextView timeView;
    TextView passView;
    ImageView imageView;
    Button resetButton;
    Thread tr;
    int currentSteps = 0;
    private Context context;
    private ContentResolver contentResolver;
    long mNow;
    long mPass;
    Date mDate;


    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String startTime = "Press RESET button";
    String passTime = "";
    public static Tab3Activity newInstance() {
        Tab3Activity fragment = new Tab3Activity();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        currentSteps = PreferenceManager.getInt(getActivity(),"rebuild");
        startTime = PreferenceManager.getString(getActivity(),"starttime");
        passTime = PreferenceManager.getString(getActivity(),"passtime");
        mNow = PreferenceManager.getLong(getActivity(),"mnow");
        Log.i("oncreate:",(startTime));
        Log.i("oncreate:",Integer.toString(currentSteps));
        // 리셋 버튼 추가 - 리셋 기능


    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(getActivity(), "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

        View root = inflater.inflate(R.layout.fragment_third, container, false);
        stepCountView = root.findViewById(R.id.textView2);
        resetButton = root.findViewById(R.id.button6);
        timeView = root.findViewById(R.id.textView);
        passView = root.findViewById(R.id.textView3);
        imageView = root.findViewById(R.id.imageView);
        stepCountView.setText(String.valueOf(currentSteps));
        timeView.setText(startTime);
        final Animation runningAnim = AnimationUtils.loadAnimation(context,R.anim.running);
        final Animation growingAnim = AnimationUtils.loadAnimation(context,R.anim.growing);
        if(currentSteps < 10) {
            imageView.setImageResource(R.drawable.monster);
        }
        if(currentSteps >= 10) {
            imageView.setImageResource(R.drawable.ingfish);
        }
        if(currentSteps >= 20) {
            imageView.setImageResource(R.drawable.gyara);
        }
        Log.i("Start:", startTime);
        tr = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        Thread.sleep(1000);
                        if(getActivity()!=null){
                            getActivity().runOnUiThread(new Runnable() // start actions in UI thread
                            {
                                @Override
                                public void run()
                                {
                                    passView.setText(passTime());
                                    PreferenceManager.setString(context,"passtime",passTime);
                                }
                            });
                        }

                    }
                    catch (InterruptedException e)
                    {
                    }
            }
        });
        tr.start();
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 걸음수 초기화
                currentSteps = 0;
                stepCountView.setText(String.valueOf(currentSteps));
                timeView.setText(getTime());
                PreferenceManager.setString(context,"starttime",getTime());
                imageView.setImageResource(R.drawable.monster);
                Log.i("startTime:", startTime);
                Log.i("onclick:",Integer.toString(currentSteps));
            }
        });
        return root;
    }
    public void onStart() {
        super.onStart();
        if(stepCountSensor !=null) {
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        final Animation runningAnim = AnimationUtils.loadAnimation(context,R.anim.running);
        final Animation growingAnim = AnimationUtils.loadAnimation(context,R.anim.growing);
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            if(event.values[0]==1.0f){
                imageView.startAnimation(runningAnim);
                currentSteps++;
                stepCountView.setText(String.valueOf(currentSteps));
                if(currentSteps == 10) {
                    imageView.setImageResource(R.drawable.ingfish);
                    imageView.startAnimation(growingAnim);
                }
                if(currentSteps == 20) {
                    imageView.setImageResource(R.drawable.gyara);
                    imageView.startAnimation(growingAnim);
                }
                PreferenceManager.setInt(context,"rebuild",currentSteps);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        PreferenceManager.setLong(getActivity(),"mnow",mNow);
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
    private String passTime(){
        long temp = System.currentTimeMillis();
        Log.i("now:",Long.toString(mNow));
        Log.i("temp:",Long.toString(temp));

        mPass = temp -mNow;
        long hour = mPass/(60*60*1000);
        long min = (mPass/(60*1000))%60;
        long sec = (mPass/(1000))%60;
        return hour+":"+min+":"+sec;
    }
    @Override

    public void onDestroy( ) {

        super.onDestroy( );

        tr.interrupt( );

    }
}
