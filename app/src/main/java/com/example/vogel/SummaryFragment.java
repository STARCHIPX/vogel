package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class SummaryFragment extends Fragment {
    private TextView textViewSummary;
    private Button buttonConfirm;
    private Button buttonBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewSummary = view.findViewById(R.id.textViewSummary);
        buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonBack = view.findViewById(R.id.buttonBack);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String selectedOption = bundle.getString("selectedOption");
            String selectedTimeOfDay = bundle.getString("selectedTimeOfDay");
            String selectedAreaName = bundle.getString("selectedAreaName"); // Der Name der ausgewählten Fläche
            ArrayList<GeoPoint> selectedArea = bundle.getParcelableArrayList("selectedArea");

            StringBuilder summary = new StringBuilder();
            summary.append("Selected Option: ").append(selectedOption).append("\n");
            summary.append("Selected Time of Day: ").append(selectedTimeOfDay).append("\n");
            summary.append("Selected Area Name: ").append(selectedAreaName).append("\n");
            summary.append("Selected Area: ").append("\n");
            for (GeoPoint point : selectedArea) {
                summary.append(point.getLatitude()).append(", ").append(point.getLongitude()).append("\n");
            }

            textViewSummary.setText(summary.toString());
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle != null) {
                    String selectedOption = bundle.getString("selectedOption");
                    String selectedTimeOfDay = bundle.getString("selectedTimeOfDay");
                    String selectedAreaName = bundle.getString("selectedAreaName");

                    DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                    boolean isInserted = dbHelper.insertSelection(selectedOption, selectedTimeOfDay, selectedAreaName);

                    if (isInserted) {
                        Toast.makeText(getContext(), "Saved successfully!", Toast.LENGTH_SHORT).show();
                        // Navigieren zum OverviewFragment
                        OverviewFragment overviewFragment = new OverviewFragment();
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, overviewFragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        Toast.makeText(getContext(), "Save failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
    }
}
