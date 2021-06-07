package com.example.myftinessapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class TimerActivity extends MainActivity implements View.OnClickListener {

    private long startInMiliis = 0;
    private long timeLeft = 0;
    private CountDownTimer countDownTimer;
    private boolean startStop = false;
    private boolean onPause = false;
    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    private int count = 0;
    private boolean stopGyroaAceler = false;
    private int exerciseType;
    TextView timeText;
    Button addTimeTButton;
    Button removeTimeTButton;
    TextView textRepeats;
    Button startStopButtonT;
    Button backButtonT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        exerciseType = getIntent().getExtras().getInt("type");
        backButtonT = (Button) findViewById(R.id.backButtonT);
        backButtonT.setOnClickListener((View.OnClickListener) this);
        timeText = (TextView) findViewById(R.id.timeText);
        textRepeats = (TextView) findViewById(R.id.textRepeats);

        addTimeTButton = (Button) findViewById(R.id.addTimeTButton);
        addTimeTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!startStop && !onPause) {
                    startInMiliis += 5000;
                    timeLeft = startInMiliis;
                    String time = String.format(Locale.getDefault(),"%02d:%03d",(int) startInMiliis/1000,startInMiliis%1000);
                    timeText.setText(time);
                }
            }
        });

        removeTimeTButton = (Button) findViewById(R.id.removeTimeTButton);
        removeTimeTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!startStop && (startInMiliis!=0) && onPause) {
                    startInMiliis -= 5000;
                    timeLeft = startInMiliis;
                    updatedTime();
                }
            }
        });

        startStopButtonT = findViewById(R.id.startStopButtonT);
        startStopButtonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStop) {
                    pauseTimer();
                }else {
                    startTimer();
                }
            }
        });

        gyroscope = new Gyroscope(this);
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if(rx < -0.3f && !stopGyroaAceler && (exerciseType == 1)){
                    count ++;
                    stopGyroaAceler = true;
                    String text = Integer.toString(count);
                    textRepeats.setText(text);
                }
                if(rx > 0.3f && (exerciseType == 1)){
                stopGyroaAceler = false;
                }

                if(rx > 1.0f && !stopGyroaAceler && (exerciseType == 0)){
                    count ++;
                    stopGyroaAceler = true;
                    String text = Integer.toString(count);
                    textRepeats.setText(text);
                }
                if(rx < -1.0f && (exerciseType == 0)){
                    stopGyroaAceler = false;
                }
            }
        });


        updatedTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscope.register();
    }

    public void onClick(View v){finish();}

    private  void startTimer() {
        countDownTimer = new CountDownTimer(startInMiliis, 5) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updatedTime();

            }

            @Override
            public void onFinish() {
                startStop = false;
                startStopButtonT.setText("Start");
            }
        }.start();

        startStop = true;
        startStopButtonT.setText("Stop");
    }

    private  void pauseTimer() {
        countDownTimer.cancel();
        startStop = false;
        startStopButtonT.setText("Start");
    }

    private void updatedTime(){
        String time = String.format(Locale.getDefault(),"%02d:%03d",(int) timeLeft/1000,timeLeft%1000);
        timeText.setText(time);
    }
}
