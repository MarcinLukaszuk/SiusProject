package com.example.marcin.siusproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.inverce.mod.core.IM;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class ShooterFragment extends Fragment {
    private static final String QUERY_URL = "https://webserviceaplikacjemobilne.conveyor.cloud/api/ShooterEventCompetitions";
    private static String competitionEventId;
    private ListView listView;
    private ShooterJSONAdapter mJSONAdapter;

    public static Fragment newInstance(String _competitionEventId) {
        competitionEventId = _competitionEventId;

        return new ShooterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shooters, parent, false);
        listView = rootView.findViewById(R.id.shooterListView);
        mJSONAdapter = new ShooterJSONAdapter(IM.context(), inflater);
        listView.setAdapter(mJSONAdapter);
        UpdateData();


        return rootView;
    }

    private void UpdateData() {
        new AsyncHttpClient().get(QUERY_URL + "/" + competitionEventId,
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


}
