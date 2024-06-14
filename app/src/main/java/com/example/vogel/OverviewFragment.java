package com.example.vogel;

import android.graphics.Color;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class OverviewFragment extends Fragment {

    //private TextView databaseValuesTextView;
    private LinearLayout databaseValuesLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseValuesLayout = view.findViewById(R.id.databaseValuesLayout);

        // Finden Sie den Button und setzen Sie den OnClickListener
        Button navigateToMapButton = view.findViewById(R.id.ButtonNext);
        navigateToMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).showMapFragment();
            }
        });

        // Datenbankzugriff und Werte abrufen
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getSelections();

        while (cursor.moveToNext()) {
            String option = cursor.getString(cursor.getColumnIndexOrThrow("option"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String timeOfDay = cursor.getString(cursor.getColumnIndexOrThrow("time_of_day"));
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

            TextView dateTextView = new TextView(getContext());
            dateTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            dateTextView.setText(date);
            innerLayout.addView(dateTextView);

            TextView timestampTextView = new TextView(getContext());
            timestampTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            timestampTextView.setText(timestamp);
            innerLayout.addView(timestampTextView);

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

            TextView polygonsTextView = new TextView(getContext());
            polygonsTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            polygonsTextView.setText("Polygons: " + polygons);
            innerLayout.addView(polygonsTextView);

            entryCardView.addView(innerLayout);
            databaseValuesLayout.addView(entryCardView);
        }
        cursor.close();
    }
}