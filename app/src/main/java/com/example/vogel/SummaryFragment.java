package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class SummaryFragment extends Fragment {

    private TextView selectedOptionTextView;
    private TextView selectedTimeOfDayTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedOptionTextView = view.findViewById(R.id.selectedOptionTextView);
        selectedTimeOfDayTextView = view.findViewById(R.id.selectedTimeOfDayTextView);

        // Empfange die übergebenen Argumente
        Bundle args = getArguments();
        if (args != null) {
            String selectedOption = args.getString("selectedOption");
            String selectedTimeOfDay = args.getString("selectedTimeOfDay");

            selectedOptionTextView.setText("Selected Option: " + selectedOption);
            selectedTimeOfDayTextView.setText("Selected Time of Day: " + selectedTimeOfDay);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
