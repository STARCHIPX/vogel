package com.example.vogel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MapFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialisiere osmdroid
        Configuration.getInstance().load(getContext(), getContext().getSharedPreferences("osmdroid", 0));

        // Erstelle die MapView
        MapView mapView = new MapView(getContext());
        mapView.setTileSource(TileSourceFactory.MAPNIK); // Wähle die Kartenquelle aus
        mapView.setBuiltInZoomControls(true); // Aktiviere Zoom-Steuerung
        mapView.setMultiTouchControls(true); // Aktiviere Multi-Touch-Steuerung

        // Zentriere die Karte auf eine bestimmte Position (z.B. Berlin)
        mapView.getController().setZoom(10); // Setze den Zoom-Level
        mapView.getController().setCenter(new GeoPoint(52.52, 13.405)); // Setze die Geo-Koordinaten

        return mapView;
    }
}
