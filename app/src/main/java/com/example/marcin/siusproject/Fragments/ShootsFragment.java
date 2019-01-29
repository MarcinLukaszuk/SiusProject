package com.example.marcin.siusproject.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marcin.siusproject.Adapters.ShootsJSONAdapter;
import com.example.marcin.siusproject.Helper;
import com.example.marcin.siusproject.R;
import com.example.marcin.siusproject.CustomViewHolders.ShooterModel;
import com.inverce.mod.core.IM;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class ShootsFragment extends Fragment {
    private static final String QUERY_URL = "http://192.168.56.1:45455/api/Shoots";
    private static ShooterModel shooter;
    private TextView NameTextView;
    private TextView SurnameTextView;
    private TextView BirthdateTextView;
    private TextView NationalityTextView;


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
        View rootView = inflater.inflate(R.layout.fragment_shooter_info, parent, false);

        NameTextView = rootView.findViewById(R.id.shooter_name);
        SurnameTextView = rootView.findViewById(R.id.shooter_surname);
        BirthdateTextView = rootView.findViewById(R.id.shooter_birthdate);
        NationalityTextView = rootView.findViewById(R.id.shooter_nationality);

        listView = rootView.findViewById(R.id.shootsListView);

        NameTextView.setText(shooter.Name);
        SurnameTextView.setText(shooter.Surname);
        BirthdateTextView.setText(Helper.GetDateStringFromDateString(shooter.BirthDate));
        NationalityTextView.setText(shooter.Nationality);

        mJSONAdapter = new ShootsJSONAdapter(IM.context(), inflater,getActivity());
        listView.setAdapter(mJSONAdapter);
        mJSONAdapter.updateData(parseShootsToSeries(shooter.ShootsArray));
        callAsynchronousShootersUpdate();
        return rootView;
    }

    public JSONArray parseShootsToSeries(JSONArray shootsArray) {
        JSONArray seriesArray = new JSONArray();
        double seriesResult = 0.0;
        String shootsString = "";
        JSONArray tenShootsArray= new JSONArray();

        for (int i = 0; i < shootsArray.length(); i++) {
            try {
                seriesResult += shootsArray.getJSONObject(i).getDouble("Value");
                String tmpString= shootsArray.getJSONObject(i).getDouble("Value")+"";
                shootsString += Helper.ParseDoubleToDecimalString(shootsArray.getJSONObject(i).getDouble("Value") + "") + " ";
                JSONObject shoot = new JSONObject();
                shoot.put("value", Helper.ParseDoubleToDecimalString(shootsArray.getJSONObject(i).getDouble("Value") + ""));
                tenShootsArray.put(shoot);

                if ((i + 1) % 10 == 0) {
                    JSONObject newObject = new JSONObject();
                    newObject.put("shootsString", shootsString);
                    newObject.put("result", Helper.ParseDoubleToDecimalString(String.valueOf(seriesResult)));
                    newObject.put("shoots", tenShootsArray);

                    seriesArray.put(newObject);

                    seriesResult = 0.0;
                    shootsString = "";
                    tenShootsArray = new JSONArray();
                }
            } catch (Exception ex) {

            }
        }
        try {
            if (shootsArray.length() % 10 != 0) {
                JSONObject newObject = new JSONObject();
                newObject.put("shootsString", shootsString);
                newObject.put("result", seriesResult);
                newObject.put("shoots", tenShootsArray);
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
