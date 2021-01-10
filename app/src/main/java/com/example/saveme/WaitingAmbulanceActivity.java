package com.example.saveme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WaitingAmbulanceActivity extends AppCompatActivity {
    Button btnSembunyi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_ambulance);

        btnSembunyi = (Button) findViewById(R.id.btnSembunyi);

        btnSembunyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}