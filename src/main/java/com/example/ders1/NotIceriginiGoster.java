package com.example.ders1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotIceriginiGoster extends AppCompatActivity {

    Not alinan_not;
    TextView tarih;
    EditText baslik;
    EditText icerik;
    Button kaydet;

    String olusturulma_tarihi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_icerigini_goster);

        Intent i =getIntent();

        tarih = findViewById(R.id.textViewSonGuncellemeTarihi);
        baslik = findViewById(R.id.editTextBaslik);
        icerik = findViewById(R.id.editTextNotIcerik);
        kaydet = findViewById(R.id.buttonNotKaydet);

        if(i.getExtras()!=null){ // Bu şekilde bir not mu açılmış yoksa yeni not girişi mi olcak bunun kontrolünü sağlıyoruz.
            alinan_not = i.getParcelableExtra("Not");
            olusturulma_tarihi = alinan_not.getOlusturulmaTarihi();
            tarih.setText("Son Güncelleme : "+olusturulma_tarihi);
            baslik.setText(alinan_not.getBaslik());
            icerik.setText(alinan_not.getIcerik());
        }

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kaydedince olacaklar.,k
                Intent i = new Intent();
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                String zaman = currentTime+" - "+currentDate;

                Not not =new Not(icerik.getText().toString(),"admin",baslik.getText().toString(),zaman);
                i.putExtra("Not_Result", (Parcelable) not);

                if(baslik.getText().toString().matches("")&&!icerik.getText().toString().matches("")){
                    Toast.makeText(NotIceriginiGoster.this,"Başlık Boş Bırakılamaz!",Toast.LENGTH_SHORT).show();
                }else if(baslik.getText().toString().matches("")&&icerik.getText().toString().matches("")){
                    setResult(Activity.RESULT_CANCELED,i);
                    finish();
                }else{
                    setResult(Activity.RESULT_OK,i);
                    finish();
                }

            }
        });



    }
}
