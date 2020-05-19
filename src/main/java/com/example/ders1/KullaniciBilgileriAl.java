package com.example.ders1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class KullaniciBilgileriAl extends AppCompatActivity {
    // Diller spinner adaptorune verilecek ve spinner içerisinde gösterilebilecek.
    String [] diller = {"Türkçe","English","Deutch","French","Italiano","Polish","Finnish","Chinese"};
    TextView username;
    EditText gender;
    Spinner language;
    EditText weight;
    EditText height;
    RadioButton darkMode;
    RadioButton lightMode;
    EditText yas;
    Button getir;
    Button güncelle;
    SharedPreferences sharedpreferences;
    String KULLANICI_BILGILERI;
    ArrayAdapter<String> dataAdapterForLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_bilgileri_al);

    Intent i =getIntent();
    // username i bir önceki ekrandan alıyorum.
    String isim = i.getStringExtra("username");
    KULLANICI_BILGILERI = isim+"_Bilgiler";
    // Layout içerisindeki widgetlara erişiyorum.
    username = findViewById(R.id.TextViewInputUsername);
    gender = findViewById(R.id.editTextGender);
    weight = findViewById(R.id.editTextWeight);
    height = findViewById(R.id.editTextHeight);
    yas = findViewById(R.id.editTextAge);
    lightMode = findViewById(R.id.radioButtonLight);
    darkMode = findViewById(R.id.radioButtonDark);
    getir = findViewById(R.id.buttonBilgiGetir);
    güncelle = findViewById(R.id.buttonBilgiGir);

    // Dil seçeneği spinner olduğu için burada ayrı olarak göstermek istedim.
    language = findViewById(R.id.spinnerLanguage);

    // Dil için bir ArrayAdapter kullandım. Aldığı parametreler bulunduğu activity, spinner için gerekli olan gösterim doğal olarak tanımlanmış
    //bulunuyor ilave olarak ben tanımlama yapmadım. Son olarak da oluşturduğum diller listesi.
    dataAdapterForLanguage = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,diller);
    // Gösterilme şekli olan resource tipini seçiyoruz.
    dataAdapterForLanguage.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

    // Seçilen adaptor ataması yapılır.
    language.setAdapter(dataAdapterForLanguage);

    // Username için gösterilen alana isim ataması yapılır.
    username.setText(isim);

    // Dosya kaydı için bir xml file açılır.
    sharedpreferences = getSharedPreferences(KULLANICI_BILGILERI, Context.MODE_PRIVATE);

        güncelle.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        String isim = username.getText().toString();
        String cinsiyet=gender.getText().toString();
        int dil = language.getSelectedItemPosition();
        String kilo = weight.getText().toString();
        String boy = height.getText().toString();
        boolean modLight = lightMode.isChecked();
        boolean modDark = darkMode.isChecked();
        String age = yas.getText().toString();

        if(cinsiyet.matches("")||kilo.matches("")||boy.matches("")||age.matches("")||(!modLight&&!modDark)){
            Toast.makeText(KullaniciBilgileriAl.this,"Tum Alanları Doldurunuz.",Toast.LENGTH_SHORT).show();
        }
        else{
        // Shared preferences içerisine yazmak için edit edilir.
        SharedPreferences.Editor editor = sharedpreferences.edit();

        // İçerisine gerekli yazmalar yapılır.
        editor.putString("isim", isim);
        editor.putString("cinsiyet", cinsiyet);
        editor.putInt("dil",dil);
        editor.putString("kilo",kilo);
        editor.putString("boy",boy);
        editor.putBoolean("modLight",modLight);
        editor.putBoolean("modDark",modDark);
        editor.putString("yas",age);

        // Yapılan değişiklikler kayıt edilir.
        editor.commit();
            Toast.makeText(KullaniciBilgileriAl.this,"İşleminiz Gerçekleşti!",Toast.LENGTH_SHORT).show();
        }
      }
    });

        getir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kaydedilen verinin okunması kısmı bu şekilde yapılır.
                //String isim = sharedpreferences.getString("isim",null);
                String cinsiyet=sharedpreferences.getString("cinsiyet",null);
                int dil = sharedpreferences.getInt("dil",0);
                String kilo = sharedpreferences.getString("kilo",null);
                String boy =sharedpreferences.getString("boy",null);
                Boolean modLight = sharedpreferences.getBoolean("modLight",false);
                Boolean modDark = sharedpreferences.getBoolean("modDark",false);
                String age = sharedpreferences.getString("yas",null);

                // Layouttaki gerekli widgetlar içerisine atamalar yapılır.
                //username.setText(isim);
                gender.setText(cinsiyet);
                language.setSelection(dil);
                weight.setText(kilo);
                height.setText(boy);
                lightMode.setChecked(modLight);
                darkMode.setChecked(modDark);
                yas.setText(age);
            }
        });
    }
}
