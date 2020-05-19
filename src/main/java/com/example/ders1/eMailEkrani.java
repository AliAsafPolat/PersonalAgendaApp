package com.example.ders1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class eMailEkrani extends AppCompatActivity {
    Button btnGonder;
    EditText mail;
    EditText to;
    EditText subject;
    ImageView attachment;
    TextView attachText;

    private static final int GALERIDEN_SEC = 101;
    String attachmentFile;
    int columnIndex;
    Uri URI=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_mail_ekrani);

        btnGonder = findViewById(R.id.ButtonGonder);
        mail = findViewById(R.id.EditTextMail);
        to = findViewById(R.id.EditTextToInput);
        subject = findViewById(R.id.EditTextSubjectInput);
        attachment = findViewById(R.id.ImageViewAttachment);
        attachText = findViewById(R.id.textVeiwAttachment);

        btnGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                String icerik = mail.getText().toString();
                String konu = subject.getText().toString();
                String kime = to.getText().toString();

                try{
                    if(icerik!=null){
                        if(konu!=null){
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT,konu);
                            emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{kime});
                            if (URI != null) {
                                emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                            }
                            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, icerik);
                            startActivity(Intent.createChooser(emailIntent,"Sending email..."));
                        }else{
                            Toast.makeText(eMailEkrani.this,"Konu Kısmı Boş Geçilemez!",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(eMailEkrani.this,"Boş Mail Gönderilemez!",Toast.LENGTH_LONG).show();
                    }
                }catch (Throwable t){
                    Toast.makeText(eMailEkrani.this, "Request failed try again: " + t.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.putExtra("return-data",true);
                    startActivityForResult(Intent.createChooser(intent,"Comple action using"),GALERIDEN_SEC);

                }catch (Throwable t){

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALERIDEN_SEC && resultCode == RESULT_OK) {
            URI = data.getData();
            attachText.setText(URI.getLastPathSegment()+"-"+URI.getEncodedPath());

            attachText.setVisibility(View.VISIBLE);
        }
    }
}
