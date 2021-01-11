package com.example.saveme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveme.Api.Client;
import com.example.saveme.Api.Interface;
import com.example.saveme.Model.Auth.DataLaporan;
import com.example.saveme.Model.Auth.Login;
import com.example.saveme.Model.Auth.LoginData;
import com.example.saveme.Model.Auth.RumahSakitData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Base64;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporActivity extends AppCompatActivity implements LocationListener {
    TextView textLongitude, textLatitude;
    EditText editKejadian, editDeskripsi;
    Button btnKirimLaporan, btnFoto;
    ImageView imageFoto;
    Integer id = 0, toastCounter=0;
    String namaFile, base64Foto, pelapor;
    Interface mApiInterface;
    RumahSakitData rumahSakitData = new RumahSakitData();

    private LocationManager locationManager;
//    private LocationListener locationListener;

    private static final int KODE_KAMERA = 123;
    private static final int UPDATE_GPS = 5000; //milisecond
    private static final int PERMISSION_WRITE_FILE = 321;
    private static final int REQUEST_PERMISSION = 999;
    private static final int MY_PERMISSION_WRITE_FILE = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapor);
        askWritePermission();

        textLatitude = (TextView) findViewById(R.id.txtLat);
        textLongitude = (TextView) findViewById(R.id.txtLong);

        pelapor = getIntent().getStringExtra("Pelapor");

        editKejadian = (EditText) findViewById(R.id.editKejadian);
        editDeskripsi = (EditText) findViewById(R.id.editDsekripsi);

        btnFoto = (Button) findViewById(R.id.btnFoto);
        btnKirimLaporan = (Button) findViewById(R.id.btnKirim);
        imageFoto = (ImageView) findViewById(R.id.imageView2);

        mApiInterface = Client.getClient().create(Interface.class);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_GPS, 0, this);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentKamera, KODE_KAMERA);
            }
        });

        btnKirimLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimLaporan();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case KODE_KAMERA:
                    try {
                        prosesKamera(data);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        textLatitude.setText(String.valueOf(location.getLatitude()));
        textLongitude.setText(String.valueOf(location.getLongitude()));
//        Toast.makeText(LaporActivity.this, "GPS Captured", Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void prosesKamera(Intent kamera) throws IOException {
        Bitmap bm;
        bm = (Bitmap) kamera.getExtras().get("data");
        imageFoto.setImageBitmap(bm);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        Date tanggalSekarang = new Date();
        String formatNama = new SimpleDateFormat("yyyyMMdd_HHmmss").format(tanggalSekarang);
        namaFile = formatNama + ".png";
        File output = new File(dir, namaFile);
        FileOutputStream fo = new FileOutputStream(output);
        fo.write(byteArray);
        fo.flush();
        fo.close();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            base64Foto = Base64.getEncoder().encodeToString(byteArray);
        }
        Toast.makeText(this, "Foto Tersimpan", Toast.LENGTH_SHORT).show();
    }

    private void askWritePermission() {
        if(Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(cameraPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_WRITE_FILE);
            }
        }

    }

    private void kirimLaporan(){
        if(base64Foto == null || editDeskripsi.getText().toString() == null || editKejadian.getText().toString() == null) {
//            Toast.makeText(getBaseContext(), "ADA ISINYA", Toast.LENGTH_LONG).show();
            Toast.makeText(getBaseContext(), "Belum Terisi Semua", Toast.LENGTH_SHORT).show();
        }
        else {
            mApiInterface.postLaporan(pelapor, editKejadian.getText().toString(),
                    editDeskripsi.getText().toString(), Float.parseFloat(textLongitude.getText().toString()),
                    Float.parseFloat(textLatitude.getText().toString()), base64Foto)
                    .enqueue(new Callback<DataLaporan>() {
                @Override
                public void onResponse(Call<DataLaporan> call, Response<DataLaporan> response) {
                    if(response.isSuccessful()){
//                        rumahSakitData.setId(response.body().getId());
//                        rumahSakitData.setNama(response.body().getRumahsakit());
                        Toast.makeText(getBaseContext(), "Berhasil Terkirim", Toast.LENGTH_SHORT).show();
                        Intent halamanWaiting = new Intent(LaporActivity.this, WaitingAmbulanceActivity.class);
                        halamanWaiting.putExtra("Id", response.body().getId());
                        halamanWaiting.putExtra("NamaRumahSakit", response.body().getRumahsakit());
                        startActivity(halamanWaiting);
                    }
                }

                @Override
                public void onFailure(Call<DataLaporan> call, Throwable t) {
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
                    Toast.makeText(getBaseContext(), "Laporan Gagal Terkirim", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}