package com.example.saveme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveme.Model.Auth.Login;
import com.example.saveme.Api.Client;
import com.example.saveme.Api.Interface;
import com.example.saveme.Model.Auth.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnMasuk;
    TextView textDaftar;
    EditText textEmail, textPassword;
    Interface mApiInterface;
    LoginData loginData = new LoginData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDaftar = (TextView) findViewById(R.id.textDaftar);
        btnMasuk = (Button) findViewById(R.id.btnMasuk);
        textEmail = (EditText) findViewById(R.id.editEmail);
        textPassword = (EditText) findViewById(R.id.editPassword);
        mApiInterface = Client.getClient().create(Interface.class);

        textDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent halamanDaftar = new Intent(MainActivity.this, DaftarActivity.class);
                startActivity(halamanDaftar);
            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Login> postLoginExe = mApiInterface.postLogin(textEmail.getText().toString(), textPassword.getText().toString());
                postLoginExe.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
//                        Log.d("POST BERHASIL", "Foto Berhasil Terupload melalui API " + response.body().toString());
                        if(response.isSuccessful()){
                            Log.d("log softgain : ", String.valueOf(response.body().getName()));
                            SharedPreferences sgSharedPref = getApplicationContext().getSharedPreferences("sg_shared_pref", getApplicationContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = sgSharedPref.edit();
                            String token = String.valueOf(response.body().getSuccess());
                            loginData.setName(response.body().getName());
                            editor.putString("token", token);
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Login berhasil: " + loginData.getName(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext() ,"Login gagal",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Log.d("log softgain : ", String.valueOf(t));
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
//                Intent halamanHome = new Intent(MainActivity.this, HomeActivity.class);
//                startActivity(halamanHome);
            }
        });
    }
}