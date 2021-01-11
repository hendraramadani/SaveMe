package com.example.saveme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveme.Api.Client;
import com.example.saveme.Api.Interface;
import com.example.saveme.Model.Auth.DataLaporan;
import com.example.saveme.Model.Auth.RumahSakitData;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitingAmbulanceActivity extends AppCompatActivity {
    Interface mApiInterface;
    TextView debug;
    String namaRumahSakit;
    Integer idPelapor;
    RumahSakitData rumahSakitData = new RumahSakitData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_ambulance);

        debug = (TextView) findViewById(R.id.textTes);
        namaRumahSakit = getIntent().getStringExtra("NamaRumahSakit");
        idPelapor = getIntent().getIntExtra("Id", 0);
        mApiInterface = Client.getClient().create(Interface.class);
//        Toast.makeText(getApplicationContext(), "Id: " + idPelapor, Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();

//        Thread for GET Method
        final Runnable r = new Runnable() {
            public void run() {
                //Initiate your API here
                mApiInterface.getNearbyAmbulance().enqueue(new Callback<List<DataLaporan>>() {
                    @Override
                    public void onResponse(Call<List<DataLaporan>> call, Response<List<DataLaporan>> response) {
                        if(response.isSuccessful()){
                            List<DataLaporan> posts = response.body();
                            for ( DataLaporan post : posts) {
                                if(post.getId().equals(idPelapor)){
                                    if(post.getAction()){
                                        Intent halamanAmbulanceSuccess = new Intent(WaitingAmbulanceActivity.this, AmbulanceSuccessActivity.class);
                                        halamanAmbulanceSuccess.putExtra("NamaRS", namaRumahSakit);
                                        startActivity(halamanAmbulanceSuccess);
                                        handler.removeMessages(0);
                                    }
                                }
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan Saat Mengambil Data Rumah Sakit", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DataLaporan>> call, Throwable t) {
                        if (t instanceof SocketTimeoutException)
                        {
                            // "Connection Timeout";
                            Log.d("SocketTimeoutException", "SocketTimeoutException Error");
                        }
                        else if (t instanceof IOException)
                        {
                            // "Timeout";
                            Log.d("TIMEOUT", "IOException Error");
                        }
                        else
                        {
                            //Call was cancelled by user
                            if(call.isCanceled())
                            {
                                Log.d("CANCELED", "Call was cancelled forcefully");
                            }
                            else
                            {
                                //Generic error handling
                                Log.d("GENERIC" ,"Network Error :: " + t.getLocalizedMessage());
                            }
                        }
                        Toast.makeText(getBaseContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                });
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(r, 5000);

    }

}