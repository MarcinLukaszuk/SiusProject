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
import android.widget.TextView;

import com.example.marcin.siusproject.Adapters.ShooterJSONAdapter;
import com.example.marcin.siusproject.Adapters.ShootsJSONAdapter;
import com.example.marcin.siusproject.Helper;
import com.example.marcin.siusproject.R;
import com.example.marcin.siusproject.ShooterModel;
import com.inverce.mod.core.IM;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class ShootsFragment extends Fragment {
    private static final String QUERY_URL = "https://webserviceaplikacjemobilne.conveyor.cloud/api/Shoots";
    private static ShooterModel shooter;
    private TextView NameTextView;
    private TextView SurnameTextView;
    private ListView listView;
    private ShootsJSONAdapter mJSONAdapter;

    public static Fragment newInstance(ShooterModel _shooter) {
        shooter = _shooter;
        return new ShootsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shoots, parent, false);

        NameTextView = rootView.findViewById(R.id.shooter_name);
        SurnameTextView = rootView.findViewById(R.id.shooter_surname);
        listView = rootView.findViewById(R.id.shootsListView);

        NameTextView.setText(shooter.Name);
        SurnameTextView.setText(shooter.Surname);

        mJSONAdapter = new ShootsJSONAdapter(IM.context(), inflater);
        listView.setAdapter(mJSONAdapter);

        callAsynchronousShootersUpdate();
        return rootView;
    }

    public JSONArray parseShootsToSeries(JSONArray shootsArray) {
        JSONArray seriesArray = new JSONArray();
        double seriesResult = 0.0;
        String shootsString = "";
        for (int i = 0; i < shootsArray.length(); i++) {
            try {
                seriesResult += shootsArray.getJSONObject(i).getDouble("Value");
                shootsString += Helper.ParseDoubleToDecimalString(shootsArray.getJSONObject(i).getDouble("Value") + "") + " ";
                if ((i + 1) % 10 == 0) {
                    JSONObject newObject = new JSONObject();
                    newObject.put("shootsString", shootsString);
                    newObject.put("result", Helper.ParseDoubleToDecimalString(String.valueOf(seriesResult)));
                    seriesArray.put(newObject);

                    seriesResult = 0.0;
                    shootsString = "";
                }
            } catch (Exception ex) {

            }
        }
        try {
            if (shootsArray.length() % 10 != 0) {
                JSONObject newObject = new JSONObject();
                newObject.put("shootsString", shootsString);
                newObject.put("result", seriesResult);
                seriesArray.put(newObject);
            }
        } catch (Exception ex) {

        }
        return seriesArray;
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
        new AsyncHttpClient().get(QUERY_URL + "/" + shooter.ShooterEventCompetitionId,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), "Success2!", Toast.LENGTH_LONG).show();
                        JSONArray shootsArray = jsonObject.optJSONArray("shoots");
                        mJSONAdapter.updateData(parseShootsToSeries(shootsArray));
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
