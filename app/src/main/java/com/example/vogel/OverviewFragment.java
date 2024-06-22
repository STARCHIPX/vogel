package com.example.vogel;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.content.Context;

public class OverviewFragment extends Fragment {

    //private TextView databaseValuesTextView;
    private LinearLayout databaseValuesLayout;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        databaseValuesLayout = view.findViewById(R.id.databaseValuesLayout);

        // Finden Sie den Button und setzen Sie den OnClickListener
        Button navigateToMapButton = view.findViewById(R.id.ButtonNext);
        navigateToMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).showSelectionFragment();
            }
        });
        // Datenbankzugriff und Werte abrufen
        displaySelections();
    }

    private void displaySelections() {
        databaseValuesLayout.removeAllViews();

        String username = sharedPreferences.getString("username", "");
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        // Datenbankzugriff und Werte abrufen
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getSelections(isAdmin);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String option = cursor.getString(cursor.getColumnIndexOrThrow("option"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String timeOfDay = cursor.getString(cursor.getColumnIndexOrThrow("time_of_day"));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow("duration"));
            String polygons = cursor.getString(cursor.getColumnIndexOrThrow("polygons"));
            String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));

            // Erstelle eine CardView für jeden Eintrag
            CardView entryCardView = new CardView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 16); // Abstand zwischen den Einträgen
            entryCardView.setLayoutParams(layoutParams);
            entryCardView.setRadius(16); // Runde Ecken
            entryCardView.setCardBackgroundColor(Color.WHITE); // Hintergrundfarbe
            entryCardView.setCardElevation(8); // Schatten

            // Inhalt der CardView definieren
            LinearLayout innerLayout = new LinearLayout(getContext());
            innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            innerLayout.setOrientation(LinearLayout.VERTICAL);
            innerLayout.setPadding(16, 16, 16, 16);

            TextView timestampTextView = new TextView(getContext());
            timestampTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            timestampTextView.setText(timestamp);
            innerLayout.addView(timestampTextView);

            TextView dateTextView = new TextView(getContext());
            dateTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            dateTextView.setText(date);
            innerLayout.addView(dateTextView);

            TextView optionTextView = new TextView(getContext());
            optionTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            optionTextView.setText(option);
            innerLayout.addView(optionTextView);

            TextView timeOfDayTextView = new TextView(getContext());
            timeOfDayTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            timeOfDayTextView.setText(timeOfDay);
            innerLayout.addView(timeOfDayTextView);

            TextView durationTextView = new TextView(getContext());
            durationTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            durationTextView.setText(duration);
            innerLayout.addView(durationTextView);

            TextView polygonsTextView = new TextView(getContext());
            polygonsTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            polygonsTextView.setText("Polygons: " + polygons);
            innerLayout.addView(polygonsTextView);

            // Schaltfläche zum Löschen hinzufügen
            Button deleteButton = new Button(getContext());
            deleteButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            deleteButton.setText("Delete");
            innerLayout.addView(deleteButton);

            // OnClickListener für die Löschtaste
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                    if (dbHelper.deleteSelection(id, username, isAdmin)) {
                        Toast.makeText(getContext(), "Entry deleted", Toast.LENGTH_SHORT).show();
                        displaySelections();
                    } else {
                        Toast.makeText(getContext(), "Error deleting entry", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            entryCardView.addView(innerLayout);
            databaseValuesLayout.addView(entryCardView);
        }
        cursor.close();
    }
}