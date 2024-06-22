package com.example.vogel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Zeige das LoginFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }

    public void showSelectionFragment() {
        // Ersetze durch das Fragment mit der Selection
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SelectionFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("isAdmin");
        editor.apply();
        showLoginFragment();
    }

    public void showLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }

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