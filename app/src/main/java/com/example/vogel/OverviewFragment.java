package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        databaseValuesTextView = view.findViewById(R.id.databaseValuesTextView);

        // Datenbankzugriff und Werte abrufen
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getSelections();

        StringBuilder values = new StringBuilder();
        while (cursor.moveToNext()) {
            String option = cursor.getString(cursor.getColumnIndexOrThrow("option"));
            String timeOfDay = cursor.getString(cursor.getColumnIndexOrThrow("time_of_day"));
            values.append("Option: ").append(option).append(", Time of Day: ").append(timeOfDay).append("\n");
        }
        cursor.close();

        // Anzeigen der Werte
        databaseValuesTextView.setText(values.toString());
    }
}