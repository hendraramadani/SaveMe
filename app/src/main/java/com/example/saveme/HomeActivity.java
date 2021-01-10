package com.example.saveme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.saveme.Model.Auth.Login;

public class HomeActivity extends AppCompatActivity {
    TextView textKeluar, textPelapor;
    Button btnLapor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = getSharedPreferences("sg_shared_pref", MODE_PRIVATE);
        SharedPreferences.Editor ed;

        if(!sharedPrefs.contains("token")){
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textKeluar = (TextView) findViewById(R.id.textKeluar1);
        textPelapor = (TextView) findViewById(R.id.txtPelapor);

        Login dataLogin = new Login();
        textPelapor.setText(dataLogin.getName());

        btnLapor = (Button) findViewById(R.id.btnLapor);

        textKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnLapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent halamanLapor = new Intent(HomeActivity.this, LaporActivity.class);
                startActivity(halamanLapor);
            }
        });

    }
}