package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import org.osmdroid.util.GeoPoint;
import java.util.ArrayList;


public class SummaryFragment extends Fragment {

    private TextView selectedOptionTextView;
    private TextView selectedTimeOfDayTextView;
    private TextView selectedPolygonsTextView;
    private Button saveToDatabaseButton;
    private Button buttonBack;

    private String polygonsString;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedOptionTextView = view.findViewById(R.id.selectedOptionTextView);
        selectedTimeOfDayTextView = view.findViewById(R.id.selectedTimeOfDayTextView);
        selectedPolygonsTextView = view.findViewById(R.id.selectedPolygonsTextView);
        saveToDatabaseButton = view.findViewById(R.id.buttonSaveToDatabase);
        buttonBack = view.findViewById(R.id.buttonBack);

        // Empfange die übergebenen Argumente
        Bundle args = getArguments();
        if (args != null) {
            String selectedOption = args.getString("selectedOption");
            String selectedTimeOfDay = args.getString("selectedTimeOfDay");
            ArrayList<GeoPoint> selectedArea = args.getParcelableArrayList("selectedArea");

            selectedOptionTextView.setText("Selected Option: " + selectedOption);
            selectedTimeOfDayTextView.setText("Selected Time of Day: " + selectedTimeOfDay);

            // Build the polygons string
            StringBuilder polygonsStringBuilder = new StringBuilder();
            if (selectedArea != null) {
                for (GeoPoint point : selectedArea) {
                    polygonsStringBuilder.append(point.getLatitude())
                            .append(", ")
                            .append(point.getLongitude())
                            .append("\n");
                }
            }
            selectedPolygonsTextView.setText(polygonsStringBuilder.toString());
        }

        // OnClickListener für den Button setzen
        saveToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });

        // Funktionalität für den Zurück-Button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getParentFragmentManager().popBackStack();
                requireActivity().onBackPressed();
            }
        });
    }

    private void saveToDatabase() {
        // Datenbankzugriff und Schreiboperationen hier durchführen
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        String selectedOption = selectedOptionTextView.getText().toString();
        String selectedTimeOfDay = selectedTimeOfDayTextView.getText().toString();
        String polygonsString = selectedPolygonsTextView.getText().toString();

        boolean isInserted = dbHelper.insertSelection(selectedOption, selectedTimeOfDay, polygonsString);
        if (isInserted) {
            Toast.makeText(getContext(), "Selection saved to database", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new OverviewFragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), "Error saving selection to database", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}