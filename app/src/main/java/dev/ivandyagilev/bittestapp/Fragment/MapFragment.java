package dev.ivandyagilev.bittestapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.MapView;

import dev.ivandyagilev.bittestapp.Interface.MapFragmentInterface;
import dev.ivandyagilev.bittestapp.Presenter.MapPresenter;
import dev.ivandyagilev.bittestapp.R;


public class MapFragment extends Fragment implements MapFragmentInterface {

    private MapView mMapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        new MapPresenter(getContext(), mMapView);

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

    @Override
    public void startLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void permissionError() {
        Toast.makeText(getContext(), R.string.location_permission_error, Toast.LENGTH_SHORT).show();
    }
}
