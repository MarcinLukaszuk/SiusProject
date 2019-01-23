package com.example.marcin.siusproject;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;


public class ShootsActivity extends AppCompatActivity {

    private String shooterName;
    private String shooterSurname;
    private String shooterNationality;
    private String shooterBirthdate;

    private TextView nameTextView;
    private TextView surnameTextView;
    private TextView nationalityTextView;
    private TextView birthdateTextView;
    private ListView listView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoots);
        shooterName = this.getIntent().getExtras().getString("name");
        shooterSurname = this.getIntent().getExtras().getString("surname");
       // shooterBirthdate = this.getIntent().getExtras().getString("birthDate");
       // shooterNationality = this.getIntent().getExtras().getString("nationality");

        //listView = findViewById(R.id.shoots_listview);
        //nameTextView= findViewById(R.id.shooter_name);
        //surnameTextView= findViewById(R.id.shooter_surname);
       // nationalityTextView= findViewById(R.id.lity);
       // birthdateTextView= findViewById(R.id.shoots_listview);


       // mJSONAdapter = new ShooterJSONAdapter(this, getLayoutInflater());
       // listView.setAdapter(mJSONAdapter);

        //callAsynchronousTask();

        nameTextView.setText(shooterName);
        surnameTextView.setText(shooterSurname);
    }
}
