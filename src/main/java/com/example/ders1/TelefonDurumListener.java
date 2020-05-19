package com.example.ders1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class TelefonDurumListener extends BroadcastReceiver {
    TextView textView;
    String aranan_numara;
    public TelefonDurumListener(TextView textView){
        this.textView = textView;
    }
    public TelefonDurumListener(){}

    @Override
    public void onReceive(Context context, Intent intent) {

        aranan_numara = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Log.i("arama","bilgi aliniyor");
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

                if(state==TelephonyManager.CALL_STATE_RINGING){
                    //Toast.makeText(context,"Gelen Arama :"+incomingNumber,Toast.LENGTH_SHORT).show();
                    textView.setText("Gelen Arama : "+incomingNumber);
                }
                if(state==TelephonyManager.CALL_STATE_IDLE){
                    //Toast.makeText(context,"Boşta Bekliyor",Toast.LENGTH_SHORT).show();
                }
                if(state==TelephonyManager.CALL_STATE_OFFHOOK){
                    //Toast.makeText(context,"Arama yapılıyor.",Toast.LENGTH_SHORT).show();
                    if(aranan_numara!=null)
                        textView.setText("Giden Arama : "+aranan_numara);
                }
            }
        },PhoneStateListener.LISTEN_CALL_STATE);

    }
}




