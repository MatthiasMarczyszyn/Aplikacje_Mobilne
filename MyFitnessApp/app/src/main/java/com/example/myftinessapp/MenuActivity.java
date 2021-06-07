package com.example.myftinessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends MainActivity implements View.OnClickListener {

    Button seriesButton;
    Button timerButton;
    Button backMenuButton;
    int typeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_site);
        backMenuButton = (Button) findViewById(R.id.backMenuButton);
        backMenuButton.setOnClickListener((View.OnClickListener) this);

        typeValue = getIntent().getExtras().getInt("type");

        seriesButton = (Button) findViewById(R.id.seriesButton);
        seriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seriesIntent = new Intent(MenuActivity.this, SeriesActivity.class);
                seriesIntent.putExtra("type",typeValue);
                startActivity(seriesIntent);
            }
        });

        timerButton = (Button) findViewById(R.id.timerButton);
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timerIntent = new Intent(MenuActivity.this, TimerActivity.class);
                timerIntent.putExtra("type",typeValue);
                startActivity(timerIntent);
            }
        });

    }


    @Override
    public void onClick(View v){finish();}
}
