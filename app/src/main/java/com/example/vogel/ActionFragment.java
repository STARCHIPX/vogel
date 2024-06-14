package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.widget.DatePicker;


public class ActionFragment extends Fragment {
    public Spinner spinnerOptions;
    public Spinner spinnerTimeOfDay;
    private Button buttonConfirm;
    private Button buttonBack;
    private DatePicker datePicker;

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
        datePicker = view.findViewById(R.id.datePicker);
        buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonBack = view.findViewById(R.id.buttonBack);

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

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedOption = spinnerOptions.getSelectedItem().toString();
                String selectedTimeOfDay = spinnerTimeOfDay.getSelectedItem().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                String selectedDate = day + "/" + (month + 1) + "/" + year;

                if (!selectedOption.isEmpty() && !selectedTimeOfDay.isEmpty()) {
                    // Bundle erstellen, um die ausgewählten Werte zu übergeben
                    Bundle bundle = new Bundle();
                    bundle.putString("selectedOption", selectedOption);
                    bundle.putString("selectedTimeOfDay", selectedTimeOfDay);
                    bundle.putString("selectedDate", selectedDate);

                    // SummaryFragment erstellen und Bundle setzen
                    MapFragment mapFragment = new MapFragment();
                    mapFragment.setArguments(bundle);

                    // Wechsle zum MapFragment
                   //((MainActivity) getActivity()).showMapFragment();
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