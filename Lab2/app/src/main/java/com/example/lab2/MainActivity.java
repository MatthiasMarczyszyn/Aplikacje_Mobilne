package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.view.View;
import android.view.View.OnClickListener;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText editTextPayment;
    EditText editTextTip;
    EditText editTextTipPrecent;
    RatingBar ratingBarService;
    RatingBar ratingBarFood;
    Button buttonSolve;
    Button buttonClear;
    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPayment = (EditText)findViewById(R.id.editTextPayment);
        editTextTipPrecent = (EditText)findViewById(R.id.editTextTipPrecent);

        editTextTip = (EditText)findViewById(R.id.editTextTip);
        editTextTip.setInputType(EditorInfo.TYPE_NULL);

        ratingBarFood = (RatingBar)findViewById(R.id.ratingBarFood);
        ratingBarService = (RatingBar)findViewById(R.id.ratingBarService);

        buttonClear = (Button)findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextTipPrecent.setText("");
                editTextTip.setText("");
                editTextPayment.setText("");
                ratingBarFood.setRating(0);
                ratingBarService.setRating(0);
            }
        });


        buttonSolve = (Button)findViewById(R.id.buttonSolve);
        buttonSolve.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    String paymentStr = editTextPayment.getText().toString();
                    String tipStr = editTextTipPrecent.getText().toString();
                    double payment = Double.parseDouble(paymentStr);
                    double ratingService = ratingBarService.getRating();
                    double ratingFood = ratingBarFood.getRating();
                    double tipPercent = Double.parseDouble(tipStr) / 200;
                    String tip = String.valueOf(roundTwoDecimals(tipPercent * payment + (tipPercent / 10) * ratingFood * payment + (tipPercent / 10) * ratingService * payment) + "PLN");

                    editTextTip.setText(tip);
                }catch (NumberFormatException e){
                    //ignore all
                }
            }

        });
    }
}