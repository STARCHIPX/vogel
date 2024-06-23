package com.example.vogel;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment that displays a map and allows the user to select areas represented by polygons.
 * The selected area is passed to a summary fragment.
 */
public class MapFragment extends Fragment {
    private MapView mapView;
    private List<GeoPoint> selectedArea;
    private Polygon currentPolygon;
    private Polygon selectedPolygon;
    private Map<Polygon, String> polygonNames; // Map to store polygon names

    /**
     * Inflates the fragment's view and initializes the map and buttons.
     *
     * @param inflater  The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));

        mapView = view.findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        mapView.getController().setZoom(17);
        mapView.getController().setCenter(new GeoPoint(51.03378, 13.73480));

        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);
        Button buttonBack = view.findViewById(R.id.buttonBack);

        selectedArea = new ArrayList<>();
        currentPolygon = new Polygon(mapView);
        polygonNames = new HashMap<>(); // Initialize the map

        // Load existing polygons and add them to the map
        loadExistingPolygons();

        buttonConfirm.setOnClickListener(v -> {
            if (selectedPolygon != null) {
                selectedArea = selectedPolygon.getPoints();
            }

            // Get the name of the selected polygon
            String selectedAreaName = polygonNames.get(selectedPolygon);

            // Retrieve the current bundle
            Bundle bundle = getArguments();
            if (bundle == null) {
                bundle = new Bundle();
            }

            // Add the new value
            bundle.putString("selectedAreaName", selectedAreaName);

            SummaryFragment summaryFragment = new SummaryFragment();
            summaryFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, summaryFragment)
                    .addToBackStack(null)
                    .commit();
        });

        buttonBack.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    /**
     * Updates the current polygon with the selected area points and adds it to the map if not already added.
     */
    private void updatePolygon() {
        currentPolygon.setPoints(selectedArea);
        currentPolygon.setFillColor(0x10101012);
        currentPolygon.setStrokeColor(0xFF0000FF);
        currentPolygon.setStrokeWidth(5);

        if (!mapView.getOverlayManager().contains(currentPolygon)) {
            mapView.getOverlayManager().add(currentPolygon);
        }

        mapView.invalidate();
    }

    /**
     * Loads predefined polygons and adds them to the map.
     */
    private void loadExistingPolygons() {
        // HTW-Dresden Z-Gebäude
        List<GeoPoint> geoPoints1 = new ArrayList<>();
        geoPoints1.add(new GeoPoint(51.03804765119182, 13.735075452040629));
        geoPoints1.add(new GeoPoint(51.037929326564054, 13.73574099238786));
        geoPoints1.add(new GeoPoint(51.036968973960306, 13.735275656306914));
        geoPoints1.add(new GeoPoint(51.03708834619956, 13.734609390438077));
        addPolygonToMap(mapView, geoPoints1, "HTW-Dresden Z-Gebäude");

        // HTW-Dresden S-Gebäude
        List<GeoPoint> geoPoints2 = new ArrayList<>();
        geoPoints2.add(new GeoPoint(51.036871641104206, 13.735281856215181));
        geoPoints2.add(new GeoPoint(51.0356782062114, 13.734680372790432));
        geoPoints2.add(new GeoPoint(51.03561987457502, 13.735008822815491));
        geoPoints2.add(new GeoPoint(51.036157407115475, 13.735274073351867));
        geoPoints2.add(new GeoPoint(51.03609326951926, 13.735664219058169));
        geoPoints2.add(new GeoPoint(51.03622389212699, 13.73572979297978));
        geoPoints2.add(new GeoPoint(51.036314673253806, 13.735354065357605));
        geoPoints2.add(new GeoPoint(51.03682906309988, 13.735598988110166));
        addPolygonToMap(mapView, geoPoints2, "HTW-Dresden S-Gebäude");

        // Fläche 3 Park
        List<GeoPoint> geoPoints3 = new ArrayList<>();
        geoPoints3.add(new GeoPoint(51.03890004563389, 13.733483557335532));
        geoPoints3.add(new GeoPoint(51.03839610473524, 13.734999963917875));
        geoPoints3.add(new GeoPoint(51.037246828495846, 13.734472439492894));
        geoPoints3.add(new GeoPoint(51.03750750635421, 13.732826044674177));
        addPolygonToMap(mapView, geoPoints3, "Fläche 3 Park");
    }

    /**
     * Adds a polygon to the map with the given points and name, and sets up its click listener.
     *
     * @param mapView   The MapView to add the polygon to.
     * @param geoPoints The list of GeoPoints defining the polygon.
     * @param name      The name of the polygon.
     */
    private void addPolygonToMap(MapView mapView, List<GeoPoint> geoPoints, String name) {
        Polygon polygon = new Polygon(mapView);
        polygon.setPoints(geoPoints);
        polygon.setFillColor(0x10101012);
        polygon.setStrokeColor(0xFF0000FF);
        polygon.setStrokeWidth(5);

        polygon.setOnClickListener((polygon1, mapView1, eventPos) -> {
            selectedPolygon = polygon1;
            String polygonName = polygonNames.get(polygon1);
            Toast.makeText(getContext(), "Polygon selected: " + polygonName, Toast.LENGTH_SHORT).show();
            return true;
        });

        mapView.getOverlayManager().add(polygon);
        polygonNames.put(polygon, name); // Store the name in the map
    }
}
