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
import android.widget.TextView;

import com.example.marcin.siusproject.Adapters.ShooterJSONAdapter;
import com.example.marcin.siusproject.R;
import com.example.marcin.siusproject.ShooterModel;
import com.inverce.mod.core.IM;

import org.json.JSONObject;

public class ShootsFragment extends Fragment {
    private static final String QUERY_URL = "https://webserviceaplikacjemobilne.conveyor.cloud/api/ShooterEventCompetitions";
    private static ShooterModel shooter;
    private TextView NameTextView;
    private TextView SurnameTextView;
    //private ListView listView;
    // private ShooterJSONAdapter mJSONAdapter;

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

        NameTextView= rootView.findViewById(R.id.shooter_name);
        SurnameTextView= rootView.findViewById(R.id.shooter_surname);

        NameTextView.setText(shooter.Name);
        SurnameTextView.setText(shooter.Surname);
        // listView = rootView.findViewById(R.id.shooterListView);
        //  mJSONAdapter = new ShooterJSONAdapter(IM.context(), inflater);
        // listView.setAdapter(mJSONAdapter);


        return rootView;
    }
}
