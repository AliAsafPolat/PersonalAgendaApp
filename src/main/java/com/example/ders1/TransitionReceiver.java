package com.example.ders1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

public class TransitionReceiver extends BroadcastReceiver {
    TextView durum_sabit;
    TextView durum_yurume;


    public TransitionReceiver(){}

    public TransitionReceiver(TextView sabit, TextView yuru){
        this.durum_sabit = sabit;
        this.durum_yurume = yuru;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        if (ActivityTransitionResult.hasResult(intent)) {
            Log.i("broadcast_info","Bilgiler Aliniyor....");

            ActivityTransitionResult results = ActivityTransitionResult.extractResult(intent);
            for (ActivityTransitionEvent event : results.getTransitionEvents()) {
                int activityType = event.getActivityType();
                int transitionType = event.getTransitionType();
                String transitionInfo = "("+activityType+","+transitionType+","+"0)";
                Log.i("broadcast_info",transitionInfo);
                Intent in = new Intent();
                in.putExtra("transitionInfo", transitionInfo);
                LocalBroadcastManager.getInstance(context).sendBroadcast(in);
            }
            processResults(results);
        }
    }

    public void processResults(ActivityTransitionResult resultList) {

        for (ActivityTransitionEvent event : resultList.getTransitionEvents()) {

            if (event.getActivityType() == DetectedActivity.STILL) {
                durum_sabit.setText("Durma İslemi Var");
                if (event.getTransitionType() == ActivityTransition.ACTIVITY_TRANSITION_ENTER) {
                    durum_sabit.setText("Suan Sabit Duruyor..");
                } else if (event.getTransitionType() == ActivityTransition.ACTIVITY_TRANSITION_EXIT) {
                    durum_sabit.setText("Suan Sabit Degil...");
                }
            }
            else if (event.getActivityType() == DetectedActivity.WALKING) {
                durum_sabit.setText("YurumeIslemiVar");
                if (event.getTransitionType() == ActivityTransition.ACTIVITY_TRANSITION_ENTER) {
                    durum_yurume.setText("Suan Yuruyor");
                } else if (event.getTransitionType() == ActivityTransition.ACTIVITY_TRANSITION_EXIT) {
                    durum_yurume.setText("Suan Yurumuyor");
                }
            }
        }
    }
}