package com.example.vogel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/** @noinspection resource*/
public class LoginFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

        // Initialisierung der Views
        EditText editTextUsername = view.findViewById(R.id.editTextUsername);
        EditText editTextPassword = view.findViewById(R.id.editTextPassword);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonRegister = view.findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(view1 -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Hier erfolgt die Überprüfung der Anmeldedaten
            if (validateLogin(username, password)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putBoolean("isAdmin", "admin".equals(username));
                editor.apply();
                ((MainActivity) requireActivity()).showSelectionFragment();
            } else {
                // Zeige eine Fehlermeldung
                Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
        buttonRegister.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Überprüfen, ob Benutzername und Passwort nicht leer sind
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Registrieren des neuen Benutzers
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            boolean isRegistered = dbHelper.insertUser(username, password);

            if (isRegistered) {
                Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show();

                // Automatisch nach der Registrierung anmelden
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putBoolean("isAdmin", false); // Neuer Benutzer ist standardmäßig kein Admin
                editor.apply();

                ((MainActivity) requireActivity()).showSelectionFragment();
            } else {
                Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        return dbHelper.validateLogin(username, password);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}