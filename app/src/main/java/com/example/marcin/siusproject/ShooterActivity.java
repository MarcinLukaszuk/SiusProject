package com.example.marcin.siusproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
import java.util.Timer;
import java.util.TimerTask;


public class ShooterActivity extends AppCompatActivity {
    private static final String QUERY_URL = "https://webserviceaplikacjemobilne.conveyor.cloud/api/ShooterEventCompetitions";
    private String competitionEventId;
    private ListView listView;
    private ShooterJSONAdapter mJSONAdapter;
    AsyncHttpClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooter);
        competitionEventId = this.getIntent().getExtras().getString("competitionEventId");

        listView = findViewById(R.id.shooterListView);
        mJSONAdapter = new ShooterJSONAdapter(this, getLayoutInflater());
        listView.setAdapter(mJSONAdapter);

        callAsynchronousTask();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
                String name = jsonObject.optString("Name", "");
                String surname = jsonObject.optString("Surname", "");
                String birthDate = jsonObject.optString("BirthDate", "");
                String nationality = jsonObject.optString("Nationality", "");

                Intent detailIntent = new Intent(ShooterActivity.this, ShootsActivity.class);
                detailIntent.putExtra("name", name);
                detailIntent.putExtra("surname", surname);
                detailIntent.putExtra("birthDate", birthDate);
                detailIntent.putExtra("nationality", nationality);
                startActivity(detailIntent);
            }
        });
    }

    private void UpdateData() {
        client = new AsyncHttpClient();
        // final ProgressDialog mDialog;
        //mDialog = new ProgressDialog(this);
        //mDialog.setMessage("Searching for Shooters");
        // mDialog.setCancelable(false);
        //  mDialog.show();
        client.get(QUERY_URL + "/" + competitionEventId,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), "Success2!", Toast.LENGTH_LONG).show();
                        mJSONAdapter.updateData(jsonObject.optJSONArray("shooters"));
                        // mDialog.dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        //Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("omg android", statusCode + " " + throwable.getStackTrace());
                        //  mDialog.dismiss();
                    }
                });
    }


    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            UpdateData();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000); //execute in every 50000 ms
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("competitionEventId", competitionEventId);
        setResult(RESULT_OK, intent);
        finish();
    }
}
