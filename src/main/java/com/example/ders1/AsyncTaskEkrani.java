package com.example.ders1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

public class AsyncTaskEkrani extends AppCompatActivity {

    ImageView imageView= null;
    Dialog p;
    ProgressBar pb;
    Button btn_indir;
    Button btn_geri_don;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_ekrani);

        btn_geri_don = findViewById(R.id.buttonAsyncGeriDon);

        imageView = findViewById(R.id.ImageViewSyncSonuc);
        btn_indir = findViewById(R.id.btnIndir);

        btn_indir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskClass task = new AsyncTaskClass();
                task.execute(Integer.valueOf(R.drawable.a1));
            }
        });
        btn_geri_don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private class AsyncTaskClass extends AsyncTask<Integer,Integer,Integer>{

        private int id;
        @Override
        protected Integer doInBackground(Integer... integers) {
            id = integers[0];
            Random rand = new Random();
            Integer yuzde = 0;
            int say;
            while(yuzde<100){
                say = rand.nextInt(10);
                yuzde += say;
                publishProgress(yuzde);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(yuzde>100)
                yuzde = 100;


            return yuzde;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new Dialog(AsyncTaskEkrani.this);
            p.requestWindowFeature(Window.FEATURE_NO_TITLE);
            p.setCancelable(false);
            p.setContentView(R.layout.progressbar_layout);


            pb = (ProgressBar)p.findViewById(R.id.progressbar_yuvarlak);

            p.show();
        }

        @Override
        protected void onProgressUpdate(Integer... i) {
            super.onProgressUpdate(i);
            pb.setProgress(i[0]);
        }

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            if(imageView!=null){
                p.hide();
                imageView.setImageResource(id);
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            }else{
                p.show();
            }
        }
    }
}
