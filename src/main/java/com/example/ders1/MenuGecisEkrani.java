package com.example.ders1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuGecisEkrani extends AppCompatActivity {

    TextView hosgeldiniz;
    Button kullanicilar;
    Button kisi;
    Button mail;
    Button sensor;
    Button not;
    ImageView kullanici_resmi;
    Button senkron;
    Button alarm;
    Button lokasyon;
    Button durum_takibi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_gecis_ekrani);

        Intent i = getIntent();

        final String username = i.getStringExtra("username");
        final int resim = i.getIntExtra("userimage",0);

        hosgeldiniz = findViewById(R.id.textViewHosgeldin);
        kullanici_resmi = findViewById(R.id.ImageViewMenuKullaniciResmi);

        hosgeldiniz.setText("Hoşgeldin "+username+"!");
        kullanici_resmi.setImageResource(resim);

        kullanicilar = findViewById(R.id.btnKullaniciListele);
        kisi = findViewById(R.id.btnBilgiGoster);
        mail = findViewById(R.id.btnMailAt);
        sensor = findViewById(R.id.btnSensorListele);
        not = findViewById(R.id.btnNotEkrani);
        senkron = findViewById(R.id.btnSenkronSay);
        alarm = findViewById(R.id.btnAlarmEkrani);
        lokasyon = findViewById(R.id.btnLokasyonEkrani);
        durum_takibi = findViewById(R.id.btnTelefonDurum);

        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MenuGecisEkrani.this,NotEkrani.class);
                in.putExtra("username",username);
                startActivity(in);
            }
        });

        kullanicilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(MenuGecisEkrani.this, KullanicilariGoster.class);
                startActivity(in1);
            }
        });


        kisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in2 = new Intent(MenuGecisEkrani.this, KullaniciBilgileriAl.class);
                in2.putExtra("username",username);
                startActivity(in2);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuGecisEkrani.this,eMailEkrani.class);
                startActivity(i);
            }
        });

        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuGecisEkrani.this, SensorEkrani.class);
                startActivity(i);
            }
        });

        senkron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuGecisEkrani.this,AsyncTaskEkrani.class);
                startActivity(i);
            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuGecisEkrani.this,AlarmEkrani.class);
                startActivity(i);
            }
        });
        lokasyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuGecisEkrani.this,SonLokasyonBilgisiEkrani.class);
                startActivity(i);
            }
        });

        durum_takibi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (MenuGecisEkrani.this,TelefonDurumTakibi.class);
                startActivity(i);
            }
        });
    }




}
