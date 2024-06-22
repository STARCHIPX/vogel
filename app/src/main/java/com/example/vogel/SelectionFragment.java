package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;


public class SelectionFragment extends Fragment {

    private Button buttonAction;
    private Button buttonOverview;
    private Button buttonLogout;
    private SharedPreferences sharedPreferences;
    private LinearLayout databaseValuesLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        databaseValuesLayout = view.findViewById(R.id.databaseValuesLayout);

        buttonAction = view.findViewById(R.id.buttonAction);
        buttonOverview = view.findViewById(R.id.buttonOverview);
        buttonLogout = view.findViewById(R.id.buttonLogout);

        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne das Fragment für die Aktion (ActionFragment)
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ActionFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        buttonOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne das Fragment für die Übersicht (OverviewFragment)
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new OverviewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).logout();
            }
        });
    }
}