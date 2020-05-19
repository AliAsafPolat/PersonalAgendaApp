package com.example.ders1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginScreen extends AppCompatActivity {
    public ArrayList kullanicilar;
    int giris_hakki = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        final EditText username = (EditText)findViewById(R.id.editTextName);
        final EditText password = (EditText)findViewById(R.id.editTextSifre);
        final Button checkButton = (Button)findViewById(R.id.btnCheck);

        kullanicilar = Person.getData();

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person insan = checkPassword(username.getText(),password.getText(),kullanicilar);

                if(insan!=null){
                    Intent menuEkraniIntent = new Intent(LoginScreen.this,MenuGecisEkrani.class);
                    menuEkraniIntent.putExtra("username",insan.getUserName());
                    menuEkraniIntent.putExtra("userimage",insan.getResim());
                    Toast.makeText(LoginScreen.this,"Giriş Sağlandı.",Toast.LENGTH_SHORT).show();
                    giris_hakki = 3;
                    startActivity(menuEkraniIntent);
                }
                else{
                    username.setText("");
                    password.setText("");
                    giris_hakki--;
                    String mesaj = String.valueOf(giris_hakki)+" deneme hakkiniz kaldi!";

                    if(giris_hakki==0){
                        finish();
                    }else{
                        Toast.makeText(LoginScreen.this,mesaj,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private Person checkPassword(Editable username, Editable password,ArrayList<Person> kullanicilar) {
        String kisii=null;
        Person res = null;
        for(int i = 0; i < 10; i++){
            if(kullanicilar.get(i).getUserName().compareTo(username.toString())==0){
                kisii=kullanicilar.get(i).getPassword();
                res = kullanicilar.get(i);
            }
        }

        //Log.d("Names",kisii);
        if(kisii==null){
            return null;
        }else{
            if(kisii.compareTo(password.toString())==0){
                return res;
            }
            else
                return null;
        }
    }
}
