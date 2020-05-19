package com.example.ders1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class SonLokasyonBilgisiEkrani extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    Button btn_lokasyon;
    Button btn_lokasyon_gonder;
    Button btn_geri_don;
    TextView txt_lokasyon;
    Task<Location> l;


    private static final int LOCATION_SERVICE_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_son_lokasyon_bilgisi_ekrani);
        btn_lokasyon = findViewById(R.id.btnLokasyonAl);
        txt_lokasyon = findViewById(R.id.textViewLokasyonBilgileri);
        btn_lokasyon_gonder = findViewById(R.id.btnLokasyonGonder);
        btn_geri_don = findViewById(R.id.buttonLokasyonGeriDon);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btn_lokasyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Task<Location> l = fusedLocationClient.getLastLocation();
                checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,
                        LOCATION_SERVICE_CODE);
                checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                        LOCATION_SERVICE_CODE);


                l = fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(SonLokasyonBilgisiEkrani.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {

                                            Double lat = location.getLatitude();
                                            Double lon =location.getLongitude();
                                            txt_lokasyon.setText("Lat : "+String.valueOf(lat)+"- Long : "+String.valueOf(lon));

                                    }else{
                                        txt_lokasyon.setText("sikinti");
                                    }
                                }
                            });


            }
        });


        btn_lokasyon_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                l = fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(SonLokasyonBilgisiEkrani.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    Double lat = location.getLatitude();
                                    Double lon =location.getLongitude();

                                        String mesaj = "http://www.google.com/maps/place/" + lat + "," + lon;

                                        Intent i = new Intent(Intent.ACTION_SEND);
                                        i.setType("text/plain");

                                        i.putExtra(Intent.EXTRA_TEXT, mesaj);
                                        if(txt_lokasyon.getText().toString().compareTo("Lokasyon Bilgisi")!=0)
                                            startActivity(Intent.createChooser(i, "Bunun ile paylaş"));
                                        else
                                            Toast.makeText(SonLokasyonBilgisiEkrani.this,"Lokasyon Bilgisi Alınmadı.",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(SonLokasyonBilgisiEkrani.this,"Lokasyon Bilgisi Alınmadı.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });

        btn_geri_don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(SonLokasyonBilgisiEkrani.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(SonLokasyonBilgisiEkrani.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(SonLokasyonBilgisiEkrani.this,
                    "Lokasyon Erişimi Bulunmaktadır.",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if (requestCode == LOCATION_SERVICE_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SonLokasyonBilgisiEkrani.this,
                        "Lokasyon Erişim İzni Verildi",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(SonLokasyonBilgisiEkrani.this,
                        "Lokasyon Erişim İzni Reddedildi",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

}
