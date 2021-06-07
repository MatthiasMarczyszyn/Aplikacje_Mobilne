package com.example.myftinessapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class SeriesActivity extends MainActivity implements View.OnClickListener {
    private long startInMiliis = 0;
    private long timeLeft = startInMiliis;
    private CountDownTimer countDownTimer;
    private boolean startStop = true;
    private boolean onPause = false;
    private boolean restExercise = false;
    private boolean stopGyroa = false;
    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    private int exerciseType;
    private int exerciseAmount = 0;
    private int exerciseLeft = exerciseAmount;
    private int seriesAmount = 0;
    private int seriesLeft = seriesAmount;
    private Handler handler;
    TextView infoText;
    TextView timeRepeatsText;
    TextView exerciseText;
    TextView timerText;
    TextView seriesText;
    Button addRepeatButton;
    Button removeRepeatButton;
    Button addTimeButton;
    Button removeTimeButton;
    Button addSeriesButton;
    Button removeSeriesButton;
    Button startStopButtonS;
    Button backButtonS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.series);
        backButtonS = (Button) findViewById(R.id.backButtonS);
        backButtonS.setOnClickListener((View.OnClickListener) this);
        infoText = (TextView) findViewById(R.id.infoText);
        timeRepeatsText = (TextView) findViewById(R.id.timeRepeatsText);
        exerciseText = (TextView) findViewById(R.id.exerciseText);
        timerText = (TextView) findViewById(R.id.timerText);
        seriesText = (TextView) findViewById(R.id.seriesText);
        exerciseType = getIntent().getExtras().getInt("type");

        addRepeatButton = (Button) findViewById(R.id.addRepeatButton);
        addRepeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStop && !onPause && exerciseAmount <= 120) {
                    exerciseAmount++;
                    exerciseLeft = exerciseAmount;
                    String text = String.format(Locale.getDefault(), "Repeats in series: %01d", exerciseAmount);
                    exerciseText.setText(text);
                }
            }
        });

        removeRepeatButton = (Button) findViewById(R.id.removeRepeatButton);
        removeRepeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStop && !onPause && exerciseAmount > 0) {
                    exerciseAmount--;
                    exerciseLeft = exerciseAmount;
                    String text = String.format(Locale.getDefault(), "Repeats in series: %01d", exerciseLeft);
                    exerciseText.setText(text);
                }
            }
        });

        addTimeButton = (Button) findViewById(R.id.addTimeButton);
        addTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStop && !onPause) {
                    startInMiliis += 5000;
                    timeLeft = startInMiliis;
                    String text = String.format(Locale.getDefault(), "Rest time: %01d s", timeLeft / 1000);
                    timerText.setText(text);
                }
            }
        });

        removeTimeButton = (Button) findViewById(R.id.removeTimeButton);
        removeTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStop && !onPause && startInMiliis > 0) {
                    startInMiliis -= 5000;
                    timeLeft = startInMiliis;
                    String text = String.format(Locale.getDefault(), "Rest time: %01d s", timeLeft / 1000);
                    timerText.setText(text);
                }
            }
        });

        addSeriesButton = (Button) findViewById(R.id.addSeriesButton);
        addSeriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStop && !onPause && seriesAmount <= 30) {
                    seriesAmount++;
                    seriesLeft = seriesAmount;
                    String text = String.format(Locale.getDefault(), "Series: %01d", seriesLeft);
                    seriesText.setText(text);
                }
            }
        });

        removeSeriesButton = (Button) findViewById(R.id.removeSeriesButton);
        removeSeriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStop && !onPause && seriesAmount > 0) {
                    seriesAmount--;
                    seriesLeft = seriesAmount;
                    String text = String.format(Locale.getDefault(), "Series: %01d", seriesLeft);
                    seriesText.setText(text);
                }
            }
        });

        startStopButtonS = (Button) findViewById(R.id.startStopButtonS);
        startStopButtonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStop) {
                    startStop = false;
                    startStopButtonS.setText("Stop");
                } else {
                    startStop = true;
                    startStopButtonS.setText("Start");
                }
            }
        });

        gyroscope = new Gyroscope(this);
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if (rx < -0.3f && !stopGyroa && (exerciseType == 1)) {
                    exerciseLeft--;
                    stopGyroa = true;
                }
                if (rx > 0.3f && (exerciseType == 1)) {
                    stopGyroa = false;
                }

                if (rx > 1.0f && !stopGyroa && (exerciseType == 0)) {
                    exerciseLeft--;
                    stopGyroa = true;
                }
                if (rx < -1.0f && (exerciseType == 0)) {
                    stopGyroa = false;
                }
            }
        });

        startStopButtonS = findViewById(R.id.startStopButtonS);
        startStopButtonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStop) {
                    startStop = false;
                    restExercise = false;
                    doSeries();
                    startStopButtonS.setText("Stop");
                } else {
                    startStop = true;
                    startStopButtonS.setText("Start");
                }
            }
        });
        new Thread(new Runnable() {
        })
    }

    public void onClick(View v) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscope.register();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(startInMiliis, 5) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                String text = String.format(Locale.getDefault(), "%01d s", timeLeft / 1000);
                timeRepeatsText.setText(text);

            }

            @Override
            public void onFinish() {
                restExercise = false;
                seriesLeft--;
            }
        }.start();

    }

    private void pauseTimer() {
        countDownTimer.cancel();
        startStop = false;
        startStopButtonS.setText("Start");
    }


    public void doSeries(View v) {
        exerciseType = 0;
        if (!startStop) {
            if (!restExercise) {
                timeRepeatsText.setText(String.format(Locale.getDefault(), "3", exerciseLeft));
                if (exerciseType == 0) {
                    infoText.setText("Now Squats!");
                } else {
                    infoText.setText("Now PushUps!");
                }
                if (exerciseLeft == 0) {
                    restExercise = true;
                    exerciseLeft = exerciseAmount;
                    timeLeft = startInMiliis;
                }
            }
            if (restExercise) {
                infoText.setText("Now Rest!");
                startTimer();
            }
        }
    }
}
