package com.example.ders1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TelefonDurumTakibi extends AppCompatActivity {
    TelefonDurumListener listener;
    MesajReceiver mesaj_listener;
    TextView arama;
    TextView arama_bilgi;
    Button btnmesaj;
    Button btnarama;
    Button btn_geri_don;

    final int PHONE_STATE_IZIN =1011;
    final int PHONE_PROCESS_IZIN=1012;
    final int SMS_IZIN=1013;
    final int SMS_ICERIK_IZIN=1014;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefon_durum_takibi);
        arama = findViewById(R.id.textViewArama);
        arama_bilgi = findViewById(R.id.textViewAramaBilgi);
        btnarama = findViewById(R.id.buttonarama1);
        btnmesaj = findViewById(R.id.buttonmesaj1);
        btn_geri_don = findViewById(R.id.buttonDurumEkraniGeriDon);

        mesaj_listener = new MesajReceiver(arama_bilgi);

        listener = new TelefonDurumListener(arama_bilgi);
        IntentFilter filter_gelen_arama = new IntentFilter();
        filter_gelen_arama.addAction("android.intent.action.PHONE_STATE");
        IntentFilter filter_giden_arama = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        IntentFilter filter_mesaj_alma = new IntentFilter();
        filter_mesaj_alma.addAction("android.provider.Telephony.SMS_RECEIVED");

        registerReceiver(listener, filter_giden_arama);
        registerReceiver(listener,filter_gelen_arama);
        registerReceiver(mesaj_listener,filter_mesaj_alma);

        Log.i("arama","kayit islemi yapildi.");


        btn_geri_don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Run time içerisinde kullanıcıdan izinleri almak istiyorum. Bu sebeple butonlara izinleri tanımladım...
        btnarama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izinleriAl1();
            }
        });

        btnmesaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izinleriAl2();
            }
        });

    }

    // Telefonun durumuna erişmek için gerekli izin...
    public void izinleriAl1() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PHONE_STATE_IZIN);
            }
        } else {
            Toast.makeText(TelefonDurumTakibi.this,"İzinler Alınmıştır.",Toast.LENGTH_SHORT).show();
        }
    }

    // Gelen mesajlara erişmek için gerekli izin...
    public void izinleriAl2() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        SMS_IZIN);
            }
        } else {
            Toast.makeText(TelefonDurumTakibi.this,"İzinler Alınmıştır.",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PHONE_STATE_IZIN: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
            case PHONE_PROCESS_IZIN: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
            case SMS_ICERIK_IZIN: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
            case SMS_IZIN: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(listener);
    }
}
