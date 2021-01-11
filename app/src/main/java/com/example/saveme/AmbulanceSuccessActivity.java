package com.example.saveme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.saveme.Model.Auth.RumahSakitData;

public class AmbulanceSuccessActivity extends AppCompatActivity {
    Button btnSelesai;
    TextView namaRumahSakit;
    RumahSakitData rumahSakitData = new RumahSakitData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_success);

        btnSelesai = (Button) findViewById(R.id.btnSelesai);
        namaRumahSakit = (TextView) findViewById(R.id.textRumahSakit);

        namaRumahSakit.setText(getIntent().getStringExtra("NamaRS"));

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AmbulanceSuccessActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}