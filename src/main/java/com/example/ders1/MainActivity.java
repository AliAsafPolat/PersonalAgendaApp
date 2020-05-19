package com.example.ders1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent login_Intent = new Intent(this,LoginScreen.class);

        //startActivity(login_Intent);
        final Button basla = findViewById(R.id.btnBasla);
        basla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_Intent = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(login_Intent);

            }
        });

    }
}
