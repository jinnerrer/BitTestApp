package dev.ivandyagilev.bittestapp.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import dev.ivandyagilev.bittestapp.R;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment {

    private MapView mMapView;
    private GoogleMap googleMap;

    private LocationManager lm;
    private Location mLastKnownLocation;

    private final LatLng mDefaultLocation = new LatLng(55.754111, 37.614615);
    private static final int DEFAULT_ZOOM = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
            getDeviceLocation();
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void getDeviceLocation() {

        lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (lm != null) {

            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mLastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else {
                    Toast.makeText(getContext(), R.string.location_permission_error, Toast.LENGTH_SHORT).show();
                }
        }

        if (mLastKnownLocation != null) {

            LatLng myLocation = new LatLng(mLastKnownLocation.getLatitude(),
                    mLastKnownLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(myLocation).title(getString(R.string.my_location_point)).snippet(mLastKnownLocation.getLatitude() + "; " + mLastKnownLocation.getLongitude()));

            CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(16).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

    }

}
