package com.example.ders1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DialogBoxAlarmDurdurma extends Activity {
    Button btn_tamam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_box_alarm_durdurma);

        btn_tamam = findViewById(R.id.buttonAlarmDurdur);

        btn_tamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmReceiver.AlarmKapat();
                finish();
            }
        });
    }
}
