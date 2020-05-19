package com.example.ders1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class HareketTakipEkrani extends AppCompatActivity {
    List<ActivityTransition> transitions;
    ActivityTransitionRequest request;
    PendingIntent transitionPendingIntent;
    ActivityRecognitionClient activityRecognitionClient;
    TextView durum_yurume;
    TextView durum_sabit;
    //BroadcastReceiver alici;
    TransitionReceiver alici;
    private  final int YAKALAMA_KODU =1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hareket_takip_ekrani);

        //activityRecognitionClient = ActivityRecognition.getClient(this);
        activityRecognitionClient = new ActivityRecognitionClient(this);

        durum_yurume = findViewById(R.id.txtDurum);
        durum_sabit = findViewById(R.id.txtDurumSabit);

        alici = new TransitionReceiver(durum_sabit,durum_yurume);
        IntentFilter inf = new IntentFilter();
        inf.addAction("android.permission_group.PERSONAL_INFO");
        Log.i("broadcast_info",inf.getAction(0).toString());
        registerReceiver(alici, inf);


    }

    public void izinleriAl() {/*
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission_group.SENSORS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    )) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission_group.SENSORS},
                        YAKALAMA_KODU);
            }
        } else {
        }*/
    }

    public void hareketleriEkle(){
        transitions= new ArrayList<>();

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());
        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.STILL)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());
        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.STILL)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());

        request = new ActivityTransitionRequest(transitions);

        Intent intent = new Intent(this, TransitionReceiver.class);

        transitionPendingIntent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Task<Void> task = activityRecognitionClient.requestActivityUpdates(0,transitionPendingIntent);
        //Task<Void> task = ActivityRecognition.getClient(this).requestActivityTransitionUpdates(request, transitionPendingIntent);

        task.addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        Toast.makeText(HareketTakipEkrani.this,"Aktiviteler Eklendi.",Toast.LENGTH_SHORT).show();// Handle success
                    }
                }
        );

        task.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Handle error
                    }
                }
        );

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

        hareketleriEkle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // myPendingIntent is the instance of PendingIntent where the app receives callbacks.
        Task<Void> task = ActivityRecognition.getClient(this)
                .removeActivityTransitionUpdates(transitionPendingIntent);

        task.addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        transitionPendingIntent.cancel();
                    }
                }
        );

        task.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {

                    }
                }
        );

    }





}