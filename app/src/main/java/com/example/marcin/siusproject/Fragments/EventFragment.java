package com.example.marcin.siusproject.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcin.siusproject.Adapters.EventJSONAdapter;
import com.example.marcin.siusproject.R;
import com.example.marcin.siusproject.ShakeListener;
import com.inverce.mod.core.IM;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class EventFragment extends Fragment {

    private static final String QUERY_URL = "http://192.168.56.1:45455/api/events";
    private ListView listView;
    EventJSONAdapter mJSONAdapter;
    private ShakeListener mShaker;

    public static Fragment newInstance() {
        return new EventFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShaker = new ShakeListener(IM.context());
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @SuppressLint("WrongConstant")
            public void onShake() {
                UpdateData();
                Toast.makeText(IM.context(), "Events Updated",  2).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_event, parent, false);
        listView = rootView.findViewById(R.id.event_listview);
        mJSONAdapter = new EventJSONAdapter(IM.context(), inflater);
        listView.setAdapter(mJSONAdapter);
        UpdateData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mShaker.pause();
                JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
                String eventId = jsonObject.optString("Id", "");
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .hide(manager.findFragmentByTag("EventFragment"))
                        .add(R.id.main_fragment_container, CompetitionFragment.newInstance(eventId), "CompetitionFragment")
                        .addToBackStack("CompetitionFragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });
        return rootView;
    }

    private void UpdateData() {
        new AsyncHttpClient()
                .get(QUERY_URL,
                        new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                mJSONAdapter.updateData(jsonObject.optJSONArray("events"));
                            }

                            @Override
                            public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                                Log.e("omg android", statusCode + " " + throwable.getStackTrace());

                            }
                        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
