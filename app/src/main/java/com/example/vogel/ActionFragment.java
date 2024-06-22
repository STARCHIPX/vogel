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


public class ActionFragment extends Fragment {
    public Spinner spinnerOptions;
    public Spinner spinnerTimeOfDay;
    public Spinner spinnerDuration;
    private Button buttonConfirm;
    private Button buttonBack;
    private DatePicker datePicker;
    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialisierung der Views
        spinnerOptions = view.findViewById(R.id.spinnerOptions);
        spinnerTimeOfDay = view.findViewById(R.id.spinnerTimeOfDay);
        spinnerDuration = view.findViewById(R.id.spinnerDuration);
        datePicker = view.findViewById(R.id.datePicker);
        buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonBack = view.findViewById(R.id.buttonBack);

        sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

        // Spinner Aktivitäten hinzufügen
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapter);

        // Spinner Tageszeit hinzufügen
        ArrayAdapter<CharSequence> timeOfDayAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_of_day_array, android.R.layout.simple_spinner_item);
        timeOfDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeOfDay.setAdapter(timeOfDayAdapter);

        // Spinner Dauer hinzufügen
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.duration_array, android.R.layout.simple_spinner_item);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration.setAdapter(durationAdapter);

        // Setzen Sie das Mindestdatum auf das heutige Datum
        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedOption = spinnerOptions.getSelectedItem().toString();
                String selectedTimeOfDay = spinnerTimeOfDay.getSelectedItem().toString();
                String selectedDuration = spinnerDuration.getSelectedItem().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                String selectedDate = day + "/" + (month + 1) + "/" + year;

                if (!selectedOption.isEmpty() && !selectedTimeOfDay.isEmpty()) {
                    String username = sharedPreferences.getString("username", "");

                    // Bundle erstellen, um die ausgewählten Werte zu übergeben
                    Bundle bundle = new Bundle();
                    bundle.putString("selectedOption", selectedOption);
                    bundle.putString("selectedTimeOfDay", selectedTimeOfDay);
                    bundle.putString("selectedDuration", selectedDuration);
                    bundle.putString("selectedDate", selectedDate);
                    bundle.putString("username", username);

                    // SummaryFragment erstellen und Bundle setzen
                    MapFragment mapFragment = new MapFragment();
                    mapFragment.setArguments(bundle);

                    // Wechsle zum MapFragment
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, mapFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}