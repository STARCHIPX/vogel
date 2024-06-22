package com.example.vogel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;

/**
 * MainActivity class that handles the main operations and fragment transactions of the app.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created. This method sets the content view and initializes
     * the first fragment to be displayed.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Show  LoginFragment if this is the first creation
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }

    /**
     * Displays the SelectionFragment by replacing the current fragment in the fragment container.
     */
    public void showSelectionFragment() {
        // Ersetze durch das Fragment mit der Selection
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SelectionFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Displays the LoginFragment by replacing the current fragment in the fragment container.
     */
    public void showLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }

    /**
     * Logs the user out by removing the username and admin status from SharedPreferences,
     * and then displays the LoginFragment.
     */
    public void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("isAdmin");
        editor.apply();
        showLoginFragment();
    }

    /**
     * Called when the activity has detected the user's press of the back key. This method
     * checks if there are any fragments in the back stack, and if so, pops the back stack.
     * If not, it behaves as usual.
     */
    @Override
    public void onBackPressed() {
        // Überprüfe, ob der Backstack nicht leer ist
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Wenn ja, entferne das oberste Fragment im Backstack
            getSupportFragmentManager().popBackStack();
        } else {
            // Wenn der Backstack leer ist, verhalte dich wie üblich
            super.onBackPressed();
        }
    }
}