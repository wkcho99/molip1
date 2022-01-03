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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week1.PreferenceManager;
import com.example.week1.R;

public class Tab3Activity extends Fragment implements SensorEventListener {
//    Button mReset;
//    TextView mwalknum;
//    //현재 걸음 수
//    private int mSteps = 0;
//    //리스너가 등록되고 난 후의 step count
//    private int mCounterSteps = 0;
//
//
//    //센서 연결을 위한 변수
//    private SensorManager sensorManager;
//    //private Sensor accelerormeterSensor;
//    private Sensor stepCountSensor;
//
//
//    private View view;
//
//
//    public Tab3Activity() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_third, container, false);
//
//        //센서 연결[걸음수 센서를 이용한 흔듬 감지]
//        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
//        //accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//
//        if(stepCountSensor == null){
//            // Toast.makeText(this,"No Step Detect Sensor",Toast.LENGTH_SHORT).show();
//        }
//
//        mReset = view.findViewById(R.id.button6);
//        mwalknum = view.findViewById(R.id.textView2);
//
//        //초기화 버튼 : 다시 숫자를 0으로 만들어준다.
//        mReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSteps = 0;
//                mCounterSteps = 0;
//                mwalknum.setText(Integer.toString(mSteps));
//
//            }
//        });
//
//
//        return view;
//    }
//
//    public void onStart() {
//        super.onStart();
//        if(stepCountSensor !=null){
//            //센서의 속도 설정
//            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_NORMAL);
//        }
//    }
//    public void onStop(){
//        super.onStop();
//        if(sensorManager!=null){
//            sensorManager.unregisterListener(this);
//        }
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        mCounterSteps = PreferenceManager.getInt(getActivity(),"rebuild");
//        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
//            //stepcountsenersor는 앱이 꺼지더라도 초기화 되지않는다. 그러므로 우리는 초기값을 가지고 있어야한다.
//            if (mCounterSteps < 1) {
//                // initial value
//                mCounterSteps = (int) event.values[0]-mSteps;
//                PreferenceManager.setInt(getActivity(),"rebuild",mCounterSteps);
//                Log.i("mCounterSteps:",Integer.toString(mCounterSteps));
//                Log.i("mCounterSteps:",Integer.toString(mCounterSteps));
//                Log.i("mSteps:",Integer.toString(mSteps));
//            }
//            //리셋 안된 값 + 현재값 - 리셋 안된 값
//            Log.i("mCounterSteps:",Integer.toString(mCounterSteps));
//            Log.i("event.values[0]:",Integer.toString((int) event.values[0]));
//            mSteps = (int) event.values[0] - mCounterSteps;
//            mwalknum.setText(Integer.toString(mSteps));
//            Log.i("log: ", "New step detected by STEP_COUNTER sensor. Total step count: " + mSteps );
//        }
//
//    }
//
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }



    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    Button resetButton;
    // 현재 걸음 수
    int currentSteps = 0;
    //
    private Context context;
    private ContentResolver contentResolver;
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
        // 걸음 센서 연결
        // * 옵션
        // - TYPE_STEP_DETECTOR:  리턴 값이 무조건 1, 앱이 종료되면 다시 0부터 시작
        // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
        //

        currentSteps = PreferenceManager.getInt(getActivity(),"rebuild");
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
        stepCountView.setText(String.valueOf(currentSteps));
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 걸음수 초기화
                currentSteps = 0;
                stepCountView.setText(String.valueOf(currentSteps));
                Log.i("onclick:",Integer.toString(currentSteps));
            }
        });
        return root;
    }

    public void onStart() {
        super.onStart();
        if(stepCountSensor !=null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            if(event.values[0]==1.0f){
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                currentSteps++;
                stepCountView.setText(String.valueOf(currentSteps));
                PreferenceManager.setInt(context,"rebuild",currentSteps);
            }


        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
