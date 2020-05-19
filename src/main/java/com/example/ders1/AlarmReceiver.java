package com.example.ders1;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver(){}
    public static Ringtone ringtone;
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm Vakti Geldi!", Toast.LENGTH_LONG).show();

            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
            ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();

        Intent mIntent = new Intent(context,DialogBoxAlarmDurdurma.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);

    }
    public static void AlarmKapat(){
        if(ringtone!=null)
            ringtone.stop();
    }
}
