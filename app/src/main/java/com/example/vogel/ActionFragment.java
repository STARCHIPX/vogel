package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.widget.DatePicker;
import android.content.SharedPreferences;
import android.content.Context;

/**
 * A fragment that allows the user to select an activity, time of day, duration, and date.
 */
public class ActionFragment extends Fragment {
    public Spinner spinnerOptions;
    public Spinner spinnerTimeOfDay;
    public Spinner spinnerDuration;
    private DatePicker datePicker;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action, container, false);
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

        // Initialize views
        spinnerOptions = view.findViewById(R.id.spinnerOptions);
        spinnerTimeOfDay = view.findViewById(R.id.spinnerTimeOfDay);
        spinnerDuration = view.findViewById(R.id.spinnerDuration);
        datePicker = view.findViewById(R.id.datePicker);
        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);
        Button buttonBack = view.findViewById(R.id.buttonBack);

        sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

        // Add items to the spinners
        // options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapter);

        // time
        ArrayAdapter<CharSequence> timeOfDayAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_of_day_array, android.R.layout.simple_spinner_item);
        timeOfDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeOfDay.setAdapter(timeOfDayAdapter);

        // duration
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.duration_array, android.R.layout.simple_spinner_item);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration.setAdapter(durationAdapter);

        // Set the minimum date to today
        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        // Set the confirm button's click listener
        buttonConfirm.setOnClickListener(v -> {
            String selectedOption = spinnerOptions.getSelectedItem().toString();
            String selectedTimeOfDay = spinnerTimeOfDay.getSelectedItem().toString();
            String selectedDuration = spinnerDuration.getSelectedItem().toString();
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            String selectedDate = day + "/" + (month + 1) + "/" + year;

            if (!selectedOption.isEmpty() && !selectedTimeOfDay.isEmpty()) {
                String username = sharedPreferences.getString("username", "");

                // Create a bundle to pass the selected values
                Bundle bundle = new Bundle();
                bundle.putString("selectedOption", selectedOption);
                bundle.putString("selectedTimeOfDay", selectedTimeOfDay);
                bundle.putString("selectedDuration", selectedDuration);
                bundle.putString("selectedDate", selectedDate);
                bundle.putString("username", username);

                // Create MapFragment and set the bundle
                MapFragment mapFragment = new MapFragment();
                mapFragment.setArguments(bundle);

                // Navigate to MapFragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, mapFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(getContext(), "Please select an option", Toast.LENGTH_SHORT).show();
            }
        });
        // Set the back button's click listener
        buttonBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }

    /**
     * Called when the view previously created by {@link #onCreateView} has been detached from the fragment.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}