package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * SelectionFragment class that provides the main selection interface for the user.
 * It allows navigation to action, overview, and logout functionalities.
 */
public class SelectionFragment extends Fragment {

    /**
     * Called to have the fragment instantiate its user interface view. This method inflates
     * the fragment's layout.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     *                           The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selection, container, false);
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned,
     * but before any saved state has been restored in to the view. This method initializes the
     * views and sets the onClickListeners for buttons.
     *
     * @param view               The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonAction = view.findViewById(R.id.buttonAction);
        Button buttonOverview = view.findViewById(R.id.buttonOverview);
        Button buttonLogout = view.findViewById(R.id.buttonLogout);

        buttonAction.setOnClickListener(v -> {
            // Navigate to MapFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ActionFragment())
                    .addToBackStack(null)
                    .commit();
        });

        buttonOverview.setOnClickListener(v -> {
            // Navigate to OverviewFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new OverviewFragment())
                    .addToBackStack(null)
                    .commit();
        });
        buttonLogout.setOnClickListener(v -> ((MainActivity) requireActivity()).logout());
    }
}