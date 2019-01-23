package com.example.marcin.siusproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentContainer = findViewById(R.id.main_fragment_container);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment_container, EventFragment.newInstance(), "EventFragment")
                .commit();
    }
}
