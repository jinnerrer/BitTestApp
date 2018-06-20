package dev.ivandyagilev.bittestapp.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ivandyagilev.bittestapp.Fragment.ListFragment;
import dev.ivandyagilev.bittestapp.Fragment.MapFragment;
import dev.ivandyagilev.bittestapp.Interface.MainActivityInterface;
import dev.ivandyagilev.bittestapp.R;

public class MainActivity extends AppCompatActivity implements MainActivityInterface{

    @BindView(R.id.fragmentContainer)
    FrameLayout mFrameLayout;

    @BindView(R.id.titleTxt)
    TextView mTitleTxt;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private ListFragment listFragment;
    private MapFragment mapFragment;

    private boolean firstLaunch = true;

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        listFragment = new ListFragment();
        mapFragment = new MapFragment();

        mapFragment = new MapFragment();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (firstLaunch) {
            setFragment(listFragment);
            firstLaunch = false;
        }
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void stopLoading() {

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_map:
                if (isServicesOK()) {
                    setFragment(mapFragment);
                    mTitleTxt.setText(getString(R.string.map_title));
                }
                return true;
            case R.id.navigation_list:
                setFragment(listFragment);
                mTitleTxt.setText(getString(R.string.list_title));
                return true;
        }
        return false;
    };

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public boolean isServicesOK(){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, R.string.service_error_msg, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.back_to_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.log_out:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        LoginManager.getInstance().logOut();
        finish();
    }
}
