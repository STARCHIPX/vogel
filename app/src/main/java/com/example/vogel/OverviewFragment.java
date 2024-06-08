package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class OverviewFragment extends Fragment {

    private TextView databaseValuesTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //databaseValuesTextView = view.findViewById(R.id.databaseValuesTextView);
        LinearLayout databaseValuesLayout = view.findViewById(R.id.databaseValuesLayout);

        // Datenbankzugriff und Werte abrufen
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getSelections();

        while (cursor.moveToNext()) {
            String option = cursor.getString(cursor.getColumnIndexOrThrow("option"));
            String timeOfDay = cursor.getString(cursor.getColumnIndexOrThrow("time_of_day"));
            String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));

            // Erstelle eine neue TextView für jeden Eintrag
            TextView entryTextView = new TextView(getContext());
            entryTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            entryTextView.setText("Option: " + option + ", Time of Day: " + timeOfDay + ", Timestamp: " + timestamp);

            // Füge die TextView zum LinearLayout hinzu
            databaseValuesLayout.addView(entryTextView);
        }
        cursor.close();
    }
}