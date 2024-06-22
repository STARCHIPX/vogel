package com.example.vogel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import org.osmdroid.util.GeoPoint;
import java.util.ArrayList;


/** @noinspection resource*/
public class SummaryFragment extends Fragment {

    private TextView selectedOptionTextView;
    private TextView selectedTimeOfDayTextView;
    private TextView selectedDateTextView;
    private TextView selectedDurationTextView;
    private TextView selectedPolygonsTextView;
    private String username;

    //private String polygonsString;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedOptionTextView = view.findViewById(R.id.selectedOptionTextView);
        selectedTimeOfDayTextView = view.findViewById(R.id.selectedTimeOfDayTextView);
        selectedDateTextView = view.findViewById(R.id.selectedDateTextView);
        selectedDurationTextView = view.findViewById(R.id.selectedDurationTextView);
        selectedPolygonsTextView = view.findViewById(R.id.selectedPolygonsTextView);
        Button saveToDatabaseButton = view.findViewById(R.id.buttonSaveToDatabase);
        Button buttonBack = view.findViewById(R.id.buttonBack);

        // Empfange die übergebenen Argumente
        Bundle args = getArguments();
        if (args != null) {
            String selectedOption = args.getString("selectedOption");
            String selectedTimeOfDay = args.getString("selectedTimeOfDay");
            String selectedDate = args.getString("selectedDate");
            String selectedDuration = args.getString("selectedDuration");
            username = args.getString("username");
            ArrayList<GeoPoint> selectedArea = args.getParcelableArrayList("selectedArea");


            selectedOptionTextView.setText("Selected Option: " + selectedOption);
            selectedTimeOfDayTextView.setText("Selected Time of Day: " + selectedTimeOfDay);
            selectedDateTextView.setText("Selected Date: " + selectedDate);
            selectedDurationTextView.setText("Selected Duration: " + selectedDuration);

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
        saveToDatabaseButton.setOnClickListener(v -> saveToDatabase());

        // Funktionalität für den Zurück-Button
        buttonBack.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void saveToDatabase() {
        // Datenbankzugriff und Schreiboperationen hier durchführen
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        String selectedOption = selectedOptionTextView.getText().toString();
        String selectedTimeOfDay = selectedTimeOfDayTextView.getText().toString();
        String selectedDate = selectedDateTextView.getText().toString();
        String selectedDuration = selectedDurationTextView.getText().toString();
        String polygonsString = selectedPolygonsTextView.getText().toString();
        String username = this.username;

        boolean isInserted = dbHelper.insertSelection(selectedOption, selectedTimeOfDay, selectedDate, polygonsString, selectedDuration, username);
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