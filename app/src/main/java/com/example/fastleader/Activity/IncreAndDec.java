package com.example.fastleader.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fastleader.R;

public class IncreAndDec extends AppCompatActivity {

    Button inc,dec;
    TextView value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incre_and_dec);

        inc = findViewById(R.id.inc);
        dec = findViewById(R.id.dec);
        value = findViewById(R.id.value);


        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textvalue = value.getText().toString();
//                value.setText(textvalue);

                int value1 = Integer.parseInt(textvalue);
                value1++;

                value.setText(String.valueOf(value1));

            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textvalue = value.getText().toString();
//                value.setText(textvalue);

                int value1 = Integer.parseInt(textvalue);
                value1--;

                value.setText(String.valueOf(value1));
                value.setTypeface(null, Typeface.BOLD);

            }
        });
    }
}