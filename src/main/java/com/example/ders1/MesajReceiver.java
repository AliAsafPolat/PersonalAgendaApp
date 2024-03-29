package com.example.ders1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MesajReceiver extends BroadcastReceiver {
    TextView textView;
    public MesajReceiver(TextView tw){
        this.textView = tw;
    }
    public MesajReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Log.i("arama","mesaj geldi");
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        Toast.makeText(context,"Mesaj Geldi!",Toast.LENGTH_SHORT).show();
                        textView.setText("Mesaj : "+msg_from);
                        //String msgBody = msgs[i].getMessageBody();
                    }
                }catch(Exception e){

                }
            }
        }
    }
}
