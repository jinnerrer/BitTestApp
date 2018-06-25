package dev.ivandyagilev.bittestapp.Presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import dev.ivandyagilev.bittestapp.Interface.MapFragmentInterface;
import dev.ivandyagilev.bittestapp.R;

import static android.content.Context.LOCATION_SERVICE;

public class MapPresenter extends BasePresenter<MapFragmentInterface>{

    private MapView mMapView;
    private GoogleMap googleMap;

    private LocationManager lm;
    private Location mLastKnownLocation;

    private final LatLng mDefaultLocation = new LatLng(55.754111, 37.614615);
    private static final int DEFAULT_ZOOM = 3;

    public MapPresenter(Context context, MapView mapView){
        mMapView = mapView;

        try {
            MapsInitializer.initialize(context.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
            getDeviceLocation(context);
        });
    }

    private void getDeviceLocation(Context context) {

        lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        if (lm != null) {

            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mLastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else {
                getMvpView().permissionError();
            }
        }

        if (mLastKnownLocation != null) {

            LatLng myLocation = new LatLng(mLastKnownLocation.getLatitude(),
                    mLastKnownLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .title(context.getString(R.string.my_location_point))
                    .snippet(context.getString(R.string.latitude)
                            + String.format(context.getString(R.string.string_format_to_double),
                            mLastKnownLocation.getLatitude())
                            + "; "
                            + context.getString(R.string.longitude)
                            + String.format(context.getString(R.string.string_format_to_double),
                            mLastKnownLocation.getLongitude())));

            CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(16).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

    }

}
