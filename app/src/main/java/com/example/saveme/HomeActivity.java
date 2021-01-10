package com.example.saveme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveme.Model.Auth.Login;
import com.example.saveme.Model.Auth.LoginData;

public class HomeActivity extends AppCompatActivity {
    TextView textKeluar, textPelapor;
    Button btnLapor;
    LoginData loginData = new LoginData();

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
////        Log.d("ISI LoginData: ", loginData.getName());
//        Toast.makeText(getApplicationContext(),
//                "HOME: " + loginData.getName(), Toast.LENGTH_SHORT).show();
        textPelapor.setText(loginData.getName());

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
                halamanLapor.putExtra("Pelapor", textPelapor.getText().toString());
                startActivity(halamanLapor);
            }
        });

    }
}