package com.example.marcin.siusproject.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.marcin.siusproject.Adapters.CompetitionJSONAdapter;
import com.example.marcin.siusproject.R;
import com.inverce.mod.core.IM;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class CompetitionFragment extends Fragment {
    private static final String QUERY_URL = "http://192.168.56.1:45455/api/eventcompetitions";
    private ListView listView;
    private CompetitionJSONAdapter mJSONAdapter;
    private static String eventId;

    public static Fragment newInstance(String _eventId) {
        eventId = _eventId;
        return new CompetitionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_competition, parent, false);
        listView = rootView.findViewById(R.id.competitionListView);
        mJSONAdapter = new CompetitionJSONAdapter(IM.context(), inflater);
        listView.setAdapter(mJSONAdapter);
        getCompetitions();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
                String competitionEventId = jsonObject.optString("Id", "");
                FragmentManager manager = getActivity().getSupportFragmentManager();

                manager.beginTransaction()
                        .hide(manager.findFragmentByTag("CompetitionFragment"))
                        .add(R.id.main_fragment_container, ShooterFragment.newInstance(competitionEventId), "ShooterFragment")
                        .addToBackStack("ShooterFragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });
        return rootView;
    }

    private void getCompetitions() {
        new AsyncHttpClient().get(QUERY_URL + "/" + eventId,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Toast.makeText(getApplicationContext(), "Success2!", Toast.LENGTH_LONG).show();
                        mJSONAdapter.updateData(jsonObject.optJSONArray("competitions"));
                        // mDialog.dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        //Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        //  Log.e("omg android", statusCode + " " + throwable.getStackTrace());
                        // mDialog.dismiss();
                    }
                });
    }


}
