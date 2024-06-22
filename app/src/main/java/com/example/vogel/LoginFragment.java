package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.vogel.R;


public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialisierung der Views
        EditText editTextUsername = view.findViewById(R.id.editTextUsername);
        EditText editTextPassword = view.findViewById(R.id.editTextPassword);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Hier erfolgt die Überprüfung der Anmeldedaten
                if (validateLogin(username, password)) {
                    // Wenn die Anmeldedaten korrekt sind, wechsle
                    ((MainActivity) requireActivity()).showSelectionFragment();
                } else {
                    // Zeige eine Fehlermeldung
                    Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        // Dummy-Validierung - ersetze dies durch echte Validierung
        return "admin".equals(username) && "admin".equals(password);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}