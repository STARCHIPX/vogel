package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class SelectionFragment extends Fragment {

    private Button buttonAction;
    private Button buttonOverview;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonAction = view.findViewById(R.id.buttonAction);
        buttonOverview = view.findViewById(R.id.buttonOverview);

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
    }
}