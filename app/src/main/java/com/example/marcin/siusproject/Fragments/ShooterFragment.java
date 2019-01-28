package com.example.marcin.siusproject.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.marcin.siusproject.Adapters.ShooterJSONAdapter;
import com.example.marcin.siusproject.R;
import com.example.marcin.siusproject.ShooterModel;
import com.inverce.mod.core.IM;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

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
        mJSONAdapter = new ShooterJSONAdapter(IM.context(), inflater,getActivity());
        listView.setAdapter(mJSONAdapter);
        callAsynchronousShootersUpdate();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
                String shooterEventCompetitionId = jsonObject.optString("ShooterEventCompetitionId", "");//poprawic
                String shooterId = jsonObject.optString("Id", "");
                FragmentManager manager = getActivity().getSupportFragmentManager();

                ShooterModel shModel = new ShooterModel();
                shModel.Id = jsonObject.optString("Id", "");
                shModel.Name = jsonObject.optString("Name", "");
                shModel.Surname = jsonObject.optString("Surname", "");
                shModel.BirthDate = jsonObject.optString("BirthDate", "");
                shModel.Nationality = jsonObject.optString("Name", "");
                shModel.ShooterEventCompetitionId = jsonObject.optString("ShooterEventCompetitionId", "");

                manager.beginTransaction()
                        .hide(manager.findFragmentByTag("ShooterFragment"))
                        .add(R.id.main_fragment_container, ShootsFragment.newInstance(shModel), "ShootsFragment")
                        .addToBackStack("ShootsFragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });


        return rootView;
    }

    public void callAsynchronousShootersUpdate() {
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
