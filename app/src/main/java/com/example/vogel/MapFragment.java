package com.example.vogel;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private Button buttonBack;
    private Button buttonConfirm;
    private List<List<GeoPoint>> allPolygons = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        //osmdroid
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));

        // Initialisiere den Zurück-Button
        buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code für das Zurückkehren zum vorherigen Fragment oder zur vorherigen Aktivität
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Konvertiere die Polygone zu einem String
                String polygonsString = convertPolygonsToString(allPolygons);

                // Erstelle ein Bundle und füge den String hinzu
                Bundle bundle = new Bundle();
                bundle.putString("polygons", polygonsString);

                // Erstelle das SummaryFragment und setze die Argumente
                SummaryFragment summaryFragment = new SummaryFragment();
                summaryFragment.setArguments(bundle);

                // Wechsle zum SummaryFragment
                ((MainActivity) getActivity()).showSummaryFragment();
            }
        });

        //MapView
        MapView mapView = view.findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK); // Wähle die Kartenquelle aus
        mapView.setBuiltInZoomControls(true); // Aktiviere Zoom-Steuerung
        mapView.setMultiTouchControls(true); // Aktiviere Multi-Touch-Steuerung

        // Zentriere die Karte auf HTWD
        mapView.getController().setZoom(17); // Setze den Zoom-Level
        mapView.getController().setCenter(new GeoPoint(51.03378, 13.73480)); // Korrdinaten

        // Flächen der Bauern
        //HTW-Dresden Z-Gebäude
        List<GeoPoint> geoPoints1 = new ArrayList<>();
        geoPoints1.add(new GeoPoint(51.03804765119182, 13.735075452040629));
        geoPoints1.add(new GeoPoint(51.037929326564054, 13.73574099238786));
        geoPoints1.add(new GeoPoint(51.036968973960306, 13.735275656306914));
        geoPoints1.add(new GeoPoint(51.03708834619956, 13.734609390438077));
        addPolygonToMap(mapView, geoPoints1);
        allPolygons.add(geoPoints1);

        //HTW-Dresden S-Gebäude
        List<GeoPoint> geoPoints2 = new ArrayList<>();
        geoPoints2.add(new GeoPoint(51.036871641104206, 13.735281856215181));
        geoPoints2.add(new GeoPoint(51.0356782062114, 13.734680372790432));
        geoPoints2.add(new GeoPoint(51.03561987457502, 13.735008822815491));
        geoPoints2.add(new GeoPoint(51.036157407115475, 13.735274073351867));
        geoPoints2.add(new GeoPoint(51.03609326951926, 13.735664219058169));
        geoPoints2.add(new GeoPoint(51.03622389212699, 13.73572979297978));
        geoPoints2.add(new GeoPoint(51.036314673253806, 13.735354065357605));
        geoPoints2.add(new GeoPoint(51.03682906309988, 13.735598988110166));
        addPolygonToMap(mapView, geoPoints2);
        allPolygons.add(geoPoints2);

        //Fläche 3
        List<GeoPoint> geoPoints3 = new ArrayList<>();
        geoPoints3.add(new GeoPoint(51.03890004563389, 13.733483557335532));
        geoPoints3.add(new GeoPoint(51.03839610473524, 13.734999963917875));
        geoPoints3.add(new GeoPoint(51.037246828495846, 13.734472439492894));
        geoPoints3.add(new GeoPoint(51.03750750635421, 13.732826044674177));
        addPolygonToMap(mapView, geoPoints3);
        allPolygons.add(geoPoints3);

        return view;
    }

    private void addPolygonToMap(MapView mapView, List<GeoPoint> geoPoints) {
        Polygon polygon = new Polygon(mapView);
        polygon.setPoints(geoPoints);
        polygon.setFillColor(0x10101012); // Füllfarbe
        polygon.setStrokeColor(0xFF0000FF); // Randfarbe
        polygon.setStrokeWidth(5); // Randdicke
        mapView.getOverlayManager().add(polygon);
    }

    private String convertPolygonsToString(List<List<GeoPoint>> allPolygons) {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<GeoPoint> polygon : allPolygons) {
            for (GeoPoint point : polygon) {
                stringBuilder.append(point.getLatitude()).append(",").append(point.getLongitude()).append(";");
            }
            stringBuilder.append("|");
        }
        return stringBuilder.toString();
    }
}