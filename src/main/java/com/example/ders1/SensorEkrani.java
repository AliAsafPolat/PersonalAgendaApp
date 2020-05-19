package com.example.ders1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class SensorEkrani extends AppCompatActivity {
    TextView textViewSensorSayisi;
    TextView textViewSensorler;
    TextView textViewSayac;
    TextView accelerometerDegerleri;
    SensorManager sensorManager;
    Sensor light;
    Sensor accelerometer;
    List<Sensor> list;
    ConstraintLayout ekran_layout;
    CountDownTimer zamanlayici;
    int onceki=-1;

    int x,y,z = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_ekrani);

        ekran_layout = findViewById(R.id.linearLayoutSensorEkrani);
        accelerometerDegerleri = findViewById(R.id.accelerometerDegerleri);
        textViewSensorler = findViewById(R.id.textViewSensorler);
        textViewSensorSayisi = findViewById(R.id.textViewSensorSayisi);
        textViewSayac = findViewById(R.id.sayac);

        sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);

        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        list = sensorManager.getSensorList(Sensor.TYPE_ALL);

        //Integer count = 0;      // Toplam sensör sayısını tutar.
        String sensorler = "";
        // Sensörlerin hepsini al ve ekrana bas.
        for(Sensor sensor:list){
            //count++;
            sensorler = sensorler + sensor.getName()+ "\n";
        }
        textViewSensorSayisi.setText(textViewSensorSayisi.getText().toString()+ " " + String.valueOf(list.size()));
        textViewSensorler.setText(sensorler);

        sensorManager.registerListener(mySensorListener,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mySensorListener,light,SensorManager.SENSOR_DELAY_NORMAL);

        // Zamanlayıcıyı 15 saniye ayarlar ve değişim olmadığı sürece azalt. Değişim kontrolleri accelerometer içerisinde oluyor.
        zamanlayici = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                textViewSayac.setText("Kalan süre : " + millisUntilFinished / 1000);
            }
            public void onFinish() {

                new AlertDialog.Builder(SensorEkrani.this)
                        .setTitle("Sistem Kapandı")
                        .setMessage("Hareketsizlik sebebi ile sistemden atıldınız!")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                startActivity(intent);
                                finish();
                                System.exit(0);

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        };
        zamanlayici.start();
    }


    private SensorEventListener mySensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_LIGHT){
                if(event.values[0]<30){
                    ColorDrawable[] color = {new ColorDrawable(Color.WHITE), new ColorDrawable(Color.DKGRAY)};
                    TransitionDrawable trans = new TransitionDrawable(color);
                    if(onceki>=30||onceki==-1){
                        ekran_layout.setBackground(trans);
                        trans.startTransition(1000);
                    }

                    //ekran_layout.setBackgroundColor(Color.BLACK);
                    textViewSensorler.setTextColor(Color.WHITE);
                    textViewSensorSayisi.setTextColor(Color.WHITE);
                    textViewSayac.setTextColor(Color.WHITE);
                    accelerometerDegerleri.setTextColor(Color.WHITE);
                    onceki = (int) event.values[0];
                }
                else{

                    ColorDrawable[] color = {new ColorDrawable(Color.DKGRAY), new ColorDrawable(Color.WHITE)};
                    TransitionDrawable trans = new TransitionDrawable(color);
                    if(onceki<30||onceki==-1){
                        ekran_layout.setBackground(trans);
                        trans.startTransition(1000);
                    }

                    textViewSensorler.setTextColor(Color.BLACK);
                    textViewSensorSayisi.setTextColor(Color.BLACK);
                    textViewSayac.setTextColor(Color.BLACK);
                    accelerometerDegerleri.setTextColor(Color.BLACK);
                    onceki = (int)event.values[0];
                }
            }

            if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                // Float değerlerle işlem yapmak çok hassas oluyor bu sebeple değerlerin virgülden sonraki kısımlarıyla ilgilenmiyorum.
                if(x !=(int)event.values[0]|| y!=(int)event.values[1]||z != (int)event.values[2])
                {
                    zamanlayici.cancel();       // Eğer değişim var ise zamanlayıcıyı sıfırla ve yeniden başlat.
                    zamanlayici.start();
                }
                x = (int)event.values[0];       // Değerleri al ve ekrana bas
                y = (int)event.values[1];
                z = (int)event.values[2];

                String degerler = String.valueOf(x)+", "+String.valueOf(y)+", "+String.valueOf(z);
                accelerometerDegerleri.setText(degerler);

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Sensorun doğruluğunun değiştiği zaman kullanılıyor. Sensörün başına bir şey geldiğinde genelde karşımıza çıkıyor.
        }
    };

}
