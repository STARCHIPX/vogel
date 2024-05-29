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


public class ActionFragment extends Fragment {
    private Spinner spinnerOptions;
    private Spinner spinnerTimeOfDay;
    private Button buttonConfirm;

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
        buttonConfirm = view.findViewById(R.id.buttonConfirm);

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
                if (!selectedOption.isEmpty() && !selectedTimeOfDay.isEmpty()) {
                    // Wechsle zum MapFragment
                    ((MainActivity) getActivity()).showMapFragment();
                } else {
                    Toast.makeText(getContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        // Dummy-Validierung - ersetze dies durch echte Validierung
        return "user".equals(username) && "pass".equals(password);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}