package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    EditText textNumber;
    EditText textBody;
    EditText textWebAdress;
    EditText textIndex;
    TextView textViewFullName;
    EditText textGrade;
    Button contactBt;
    Button smsBt;
    Button webSeachBt;
    Button myActivityBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNumber = (EditText)findViewById(R.id.textNumber);
        textBody = (EditText)findViewById(R.id.textBody);
        textWebAdress = (EditText)findViewById(R.id.textWebAdress);
        textIndex = (EditText)findViewById(R.id.textIndex);
        textViewFullName = (TextView)findViewById(R.id.textViewFullName);
        textGrade = (EditText)findViewById(R.id.textGrade);
        smsBt = (Button)findViewById(R.id.SmsBt);
        smsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "smsto:" + textNumber.getText().toString();
                String smsBody = textBody.getText().toString();
                Intent intent = new Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse(number));
                intent.putExtra("sms_body",smsBody);
                startActivity(intent);

            }
        });

        webSeachBt = (Button)findViewById(R.id.WebSearchBt);
        webSeachBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webAdress = textWebAdress.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webAdress));
                startActivity(intent);
            }
        });

        contactBt = (Button)findViewById(R.id.ContactBt);
        contactBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myData = "content://contacts/people/";
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(myData));
                startActivity(intent);
            }
        });

        myActivityBt = (Button)findViewById(R.id.MyActivityBt);
        myActivityBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = textIndex.getText().toString();
                int studentIndex = Integer.parseInt(temp);
                temp = textGrade.getText().toString();
                int studentGrade = Integer.parseInt(temp);
                Intent myIntentM1M2 = new Intent (MainActivity.this, MainActivity2.class);
                Bundle myDataBundle = new Bundle();
                myDataBundle.putInt("val1",studentIndex);
                myDataBundle.putInt("val2",studentGrade);

                myIntentM1M2.putExtras(myDataBundle);
                startActivityForResult(myIntentM1M2, 101);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        try {
            if((requestCode==101)&&(resultCode == Activity.RESULT_OK))
            {
                Bundle myResultBundle = data.getExtras();
                String myResult1 = myResultBundle.getString("vresult1");
                String myResult2 = myResultBundle.getString("vresult2");
                textViewFullName.setText(myResult1 + " " + myResult2);
            }
        }
        catch (Exception e){
            textViewFullName.setText("Problems:"+ requestCode + " " + resultCode);
        }
    }
}