package com.example.marcin.siusproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

public class CompetitionActivity extends AppCompatActivity {
    private static final String QUERY_URL = "https://webserviceaplikacjemobilne.conveyor.cloud/api/eventcompetitions";
    private ListView listView;
    private CompetitionJSONAdapter mJSONAdapter;
    private ProgressDialog mDialog;
    private FrameLayout fragmentContainer;
    private   String eventId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        fragmentContainer = findViewById(R.id.fragment_container);
        eventId = this.getIntent().getExtras().getString("eventId");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, CompetitionFragment.newInstance(eventId), "CompetitionFragment")
                .commit();
//        listView = findViewById(R.id.competitionListView);
//        mJSONAdapter = new CompetitionJSONAdapter(this, getLayoutInflater());
//        listView.setAdapter(mJSONAdapter);
//
//        mDialog = new ProgressDialog(this);
//        mDialog.setMessage("Searching for Competitions");
//        mDialog.setCancelable(false);
//        mDialog.show();
//        AsyncHttpClient client = new AsyncHttpClient();
//
//        client.get(QUERY_URL + "/" + eventId,
//                new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(JSONObject jsonObject) {
//                        // Toast.makeText(getApplicationContext(), "Success2!", Toast.LENGTH_LONG).show();
//                        mJSONAdapter.updateData(jsonObject.optJSONArray("competitions"));
//                        mDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
//                        //Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
//                        Log.e("omg android", statusCode + " " + throwable.getStackTrace());
//                        mDialog.dismiss();
//                    }
//                });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//                JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
//                String competitionEventId = jsonObject.optString("Id", "");
//
//                Intent detailIntent = new Intent(CompetitionActivity.this, ShooterActivity.class);
//                detailIntent.putExtra("competitionEventId", competitionEventId);
//                startActivity(detailIntent);
//            }
//        });
    }

}
