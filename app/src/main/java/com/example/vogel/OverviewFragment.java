package com.example.vogel;

import android.annotation.SuppressLint;
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

/**
 * A fragment that displays the user's selections from the database.
 * @noinspection ALL
 */
public class OverviewFragment extends Fragment {

    private LinearLayout databaseValuesLayout;
    private SharedPreferences sharedPreferences;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    /**
     * Called immediately after {@link #onCreateView} has returned, but before any saved state has been restored in to the view.
     *
     * @param view The View returned by {@link #onCreateView}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        databaseValuesLayout = view.findViewById(R.id.databaseValuesLayout);

        // Find the button and set its OnClickListener
        Button navigateToMapButton = view.findViewById(R.id.ButtonNext);
        navigateToMapButton.setOnClickListener(v -> ((MainActivity) requireActivity()).showSelectionFragment());

        // Display selections from database
        displaySelections();
    }

    /**
     * Displays the selections stored in the database.
     */
    @SuppressLint("SetTextI18n")
    private void displaySelections() {
        databaseValuesLayout.removeAllViews();

        String username = sharedPreferences.getString("username", "");
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        // Access the database and retrieve values
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = dbHelper.getSelections();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String option = cursor.getString(cursor.getColumnIndexOrThrow("option"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String timeOfDay = cursor.getString(cursor.getColumnIndexOrThrow("time_of_day"));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow("duration"));
            String areaName = cursor.getString(cursor.getColumnIndexOrThrow("area_name")); // Name der Fläche
            String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));

            // Create a CardView for each entry
            CardView entryCardView = new CardView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 16); // space between
            entryCardView.setLayoutParams(layoutParams);
            entryCardView.setRadius(16); //rounded corners
            entryCardView.setCardBackgroundColor(Color.WHITE); // background color
            entryCardView.setCardElevation(8); // shadow

            // Define  content of the CardView
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

            TextView areaNameTextView = new TextView(getContext());
            areaNameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            areaNameTextView.setText("Area Name: " + areaName); // Fläche Name anzeigen
            innerLayout.addView(areaNameTextView);

            // Add a delete button
            Button deleteButton = new Button(getContext());
            deleteButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            deleteButton.setText("Delete");
            innerLayout.addView(deleteButton);

            // Set OnClickListener for the delete button
            deleteButton.setOnClickListener(v -> {
                DatabaseHelper dbHelper1 = new DatabaseHelper(getContext());
                if (dbHelper1.deleteSelection(id, username, isAdmin)) {
                    Toast.makeText(getContext(), "Entry deleted", Toast.LENGTH_SHORT).show();
                    displaySelections();
                } else {
                    Toast.makeText(getContext(), "Error deleting entry", Toast.LENGTH_SHORT).show();
                }
            });

            entryCardView.addView(innerLayout);
            databaseValuesLayout.addView(entryCardView);
        }
        cursor.close();
    }
}
