package com.example.myftinessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button buttonPushUp;
    Button buttonSquat;
    Button buttonHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPushUp = (Button) findViewById(R.id.buttonPushUp);
        buttonPushUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(MainActivity.this, MenuActivity.class);
                menuIntent.putExtra("type",1);
                startActivity(menuIntent);
            }
        });

        buttonSquat = (Button) findViewById(R.id.buttonSquat);
        buttonSquat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(MainActivity.this, MenuActivity.class);
                menuIntent.putExtra("type",0);
                startActivity(menuIntent);
            }
        });
    }
}