package com.example.marcin.siusproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.marcin.siusproject.Fragments.EventFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentContainer = findViewById(R.id.main_fragment_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, EventFragment.newInstance(), "EventFragment")
                    .commit();

        }
    }
}
