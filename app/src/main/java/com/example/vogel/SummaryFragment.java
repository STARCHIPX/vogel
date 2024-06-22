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

/**
 * SummaryFragment class that displays the summary of the user's selections and allows saving
 * these selections to the database.
 * @noinspection resource
 */
public class SummaryFragment extends Fragment {

    private TextView selectedOptionTextView;
    private TextView selectedTimeOfDayTextView;
    private TextView selectedDateTextView;
    private TextView selectedDurationTextView;
    private TextView selectedPolygonsTextView;
    private String username;

    /**
     * Called to have the fragment instantiate its user interface view. This method inflates
     * the fragment's layout.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     *                           The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned,
     * but before any saved state has been restored in to the view. This method initializes the
     * views and sets the onClickListeners for buttons.
     *
     * @param view               The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
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

        // Receive passed arguments
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

        // OnClickListener for back button
        saveToDatabaseButton.setOnClickListener(v -> saveToDatabase());

        // Set functionality for the back button
        buttonBack.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    /**
     * Saves the current selection to the database. If the operation is successful,
     * a toast message is displayed and the OverviewFragment is shown.
     * If the operation fails, an error toast message is displayed.
     */
    private void saveToDatabase() {
        // Database access and write operations here
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

    /**
     * Called when the view previously created by onCreateView(LayoutInflater, ViewGroup, Bundle)
     * has been detached from the fragment. Cleans up resources related to the view.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}