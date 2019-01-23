package com.example.marcin.siusproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EventActivity extends AppCompatActivity {
    private static final String QUERY_URL = "https://webserviceaplikacjemobilne.conveyor.cloud/api/events";
    private ListView listView;
    EventJSONAdapter mJSONAdapter;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        listView = findViewById(R.id.listView);
        mJSONAdapter = new EventJSONAdapter(this, getLayoutInflater());
        listView.setAdapter(mJSONAdapter);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Searching for Events");
        mDialog.setCancelable(false);
        mDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(QUERY_URL,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                        mJSONAdapter.updateData(jsonObject.optJSONArray("events"));
                        mDialog.dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("omg android", statusCode + " " + throwable.getStackTrace());
                        mDialog.dismiss();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
                String eventId = jsonObject.optString("Id", "");

                Intent detailIntent = new Intent(EventActivity.this, CompetitionActivity.class);
                detailIntent.putExtra("eventId", eventId);
                startActivity(detailIntent);
            }
        });


    }
}
