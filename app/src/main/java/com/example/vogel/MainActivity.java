package com.example.vogel;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;

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

    public void showOverviewFragment() {
        // Methode zum Wechseln zum ActionFragment nach erfolgreichem Login
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new OverviewFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void showMapFragment() {
    // Ersetze das ActionFragment durch das Fragment mit der Karte
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragment_container, new MapFragment());
    transaction.addToBackStack(null);
    transaction.commit();
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