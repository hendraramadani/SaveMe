package com.example.saveme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveme.Api.Client;
import com.example.saveme.Api.Interface;
import com.example.saveme.Model.Auth.Login;
import com.example.saveme.Model.Auth.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity extends AppCompatActivity {
    Button btnDaftar;
    EditText textNama,textEmail,textPassword,textNoHP;
    TextView textMasuk;
    Interface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        textNama = (EditText) findViewById(R.id.editNama);
        textEmail = (EditText) findViewById(R.id.editEmail);
        textPassword = (EditText) findViewById(R.id.editPassword);
        textNoHP = (EditText) findViewById(R.id.editNoHP);

        textMasuk = (TextView) findViewById(R.id.textMasuk);
        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        mApiInterface = Client.getClient().create(Interface.class);

        textMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Register> postRegisterExe = mApiInterface.postRegister(textNama.getText().toString(), textEmail.getText().toString(),
                        textPassword.getText().toString(), textNoHP.getText().toString(), "Jombang");
//                Intent halamanMasuk = new Intent(DaftarActivity.this, MainActivity.class);
//                startActivity(halamanMasuk);

                postRegisterExe.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        Toast.makeText(getApplicationContext(), "Sukses", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        Log.d("log softgain : ", String.valueOf(t));
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}