package com.example.ders1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NotEkrani extends AppCompatActivity {

    RecyclerView recyclerViewNotlar;
    NotAdapter mNotAdapter;
    ArrayList<Not> notlar;
    LinearLayoutManager linearLayoutManager;
    Button btnNotEkle;
    SharedPreferences sharedPreferences;
    String KULLANICI_NOTLARI;
    final int ICERIK_GOSTER_KOD = 101;
    final int YENI_NOT_EKLE_KOD = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_ekrani);

        Intent i = getIntent();
        KULLANICI_NOTLARI = i.getStringExtra("username");
        KULLANICI_NOTLARI = KULLANICI_NOTLARI+"_Notlar";


        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String zaman = currentTime+" - "+currentDate;



        sharedPreferences = getSharedPreferences(KULLANICI_NOTLARI, Context.MODE_PRIVATE);

        notlar = notlariGetir();
        if(notlar == null){ // İşleme ilk defa girdiğinde null olacaktır. Sonraki girişlerinde kaydedilen değerler alınacak.
            notlar=new ArrayList<Not>();
            Not or = new Not("isim","asaf","örnek",zaman);
            notlar.add(or);
            Log.d("Not_Kayit","Burada bir kayit yapildi");
        }else{
            Log.d("Not_Kayit","Notlar onCreate methodunda başarıyla yüklendi.");
        }

        btnNotEkle = findViewById(R.id.buttonNotEkle);

        mNotAdapter = new NotAdapter(this,notlar);
        recyclerViewNotlar = findViewById(R.id.recyclerViewNotlar);
        recyclerViewNotlar.setAdapter(mNotAdapter);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNotlar.setLayoutManager(linearLayoutManager);

        mNotAdapter.setOnItemClickListener(new NotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Nota tıklayınca çıkmasını istediğin ekranı buraya giriyorsun.
                Intent i = new Intent(NotEkrani.this,NotIceriginiGoster.class);
                i.putExtra("Not", (Parcelable) notlar.get(position));
                notlar.remove(position);
                startActivityForResult(i,ICERIK_GOSTER_KOD);
            }

            @Override
            public void onDeleteClick(final int position) {
                // Notu silmek istediği zaman uyarı mesajı versin.
                new AlertDialog.Builder(NotEkrani.this)
                        .setTitle("Not Sil")
                        .setMessage("Bu notu gerçekten silmek istiyor musunuz?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                notlar.remove(position);
                                mNotAdapter.notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        btnNotEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ekleme butonuna basınca çıkıcak eventi tanımlıyorsun.
                //Toast.makeText(NotEkrani.this,"Ekleme yapilacak.",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(NotEkrani.this, NotIceriginiGoster.class);
                startActivityForResult(i,YENI_NOT_EKLE_KOD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // startActivityForResult() methodu çalıştırıldığında yeni not ekleme tuşuna basıldı ise.
        if (requestCode == YENI_NOT_EKLE_KOD) {
            if(resultCode==Activity.RESULT_OK){
                Not not = data.getParcelableExtra("Not_Result");
                notlar.add(0,not);
                //mNotAdapter.notifyDataSetChanged();
                mNotAdapter.notifyItemInserted(0); // Bu şekilde animasyon ile kaldırıyoruz.
            }else if(resultCode==Activity.RESULT_CANCELED){
                // Bu durumda hiçbir değişiklik yapmama gerek yok.
            }

        // startActivityForResult() methodu çalıştırıldığında item görüntülenmek istendi ise.
        } else if (requestCode == ICERIK_GOSTER_KOD) {
            if(resultCode== Activity.RESULT_OK){            // Sonucu istendiği gibi döndürürse.
                Not not = data.getParcelableExtra("Not_Result");
                notlar.add(0,not);
                mNotAdapter.notifyDataSetChanged();
            }else if(resultCode==Activity.RESULT_CANCELED){ // Sonucu istendiği gibi döndürmez ise
                mNotAdapter.notifyDataSetChanged();         // Eğer başlık ve içerik boş hale getirilmişse not silinir.
            }
        }
    }

    @Override
    protected void onDestroy() {
        // Uygulama kapatıldığında dosyaları kaydetsin.
        super.onDestroy();
    boolean res=notlariKaydet(notlar);
        if(res)
            Log.d("Not_Kayit","Notlar basari ile kaydedildi.");
        else
                Log.e("Not_Kayit","Notlar kaydedilemedi.");
}


    @Override
    protected void onPause() {
        // Uygulama herhangi bir şekilde durduğunda gerekli kaydetme işlemlerini yapsın.
        super.onPause();
        boolean res = notlariKaydet(notlar);
        if(res)
            Log.d("Not_Kayit","Notlar basari ile kaydedildi.");
        else
            Log.e("Not_Kayit","Notlar kaydedilemedi.");

    }

    private ArrayList<Not> notlariGetir(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("notlar_dizisi",null);
        if(json == null)
            return null;
        // Kaydedilen notları getirmek için gson filedan yararlanıyoruz.
        notlar= gson.fromJson(json, new TypeToken<ArrayList<Not>>(){}.getType());
        return notlar;
    }

    private boolean notlariKaydet(ArrayList<Not> notlar_dizisi){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        Gson gson = new Gson();
        if(notlar_dizisi!=null){
            // Objeleri dosyaya yazmak için gson filedan yararlanıyoruz.
            String json=gson.toJson(notlar_dizisi);
            editor.putString("notlar_dizisi",json);
            editor.commit();
            return true;
        }
        return false;

    }
}
