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

/**
 * A fragment that handles user login and registration.
 * @noinspection ALL
 */
public class LoginFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
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

        // Initialization of views
        EditText editTextUsername = view.findViewById(R.id.editTextUsername);
        EditText editTextPassword = view.findViewById(R.id.editTextPassword);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonRegister = view.findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(view1 -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Validate login credentials
            if (validateLogin(username, password)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putBoolean("isAdmin", "admin".equals(username));
                editor.apply();
                ((MainActivity) requireActivity()).showSelectionFragment();
            } else {
                // error message
                Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
        buttonRegister.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Check if username and password are not empty
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Register the new user
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            boolean isRegistered = dbHelper.insertUser(username, password);

            if (isRegistered) {
                Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show();

                // Automatically log in after registration
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putBoolean("isAdmin", false); // New user is not an admin by default
                editor.apply();

                ((MainActivity) requireActivity()).showSelectionFragment();
            } else {
                Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Validates the user's login credentials.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return True if the login credentials are valid, false otherwise.
     */
    private boolean validateLogin(String username, String password) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        return dbHelper.validateLogin(username, password);
    }

    /**
     * Called when the view created by {@link #onCreateView} has been detached from the fragment.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}