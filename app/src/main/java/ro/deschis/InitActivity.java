package ro.deschis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public abstract class InitActivity extends BaseActivity {
    private static final int REQ_LOCATION_PERMISSION = 8721;

    private GoogleApiClient googleApiClient;
    private boolean initDone;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getCurrentLocation() == null) {
            if (ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION}, REQ_LOCATION_PERMISSION);
            }
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override public void onConnected(@Nullable Bundle bundle) {
                            if (ActivityCompat.checkSelfPermission(InitActivity.this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED) {
                                initCurrentLocation();
                            }
                        }

                        @Override public void onConnectionSuspended(int i) {
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            initDone();
                        }
                    })
                    .build();
            googleApiClient.connect();
        } else {
            initDone();
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_LOCATION_PERMISSION) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                if (googleApiClient.isConnected()) {
                    initCurrentLocation();
                }
            } else {
                googleApiClient.disconnect();
                initDone();
            }
        }
    }

    private void initCurrentLocation() {
        ((DeschisApplication) getApplication()).currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        googleApiClient.disconnect();
        initDone();
    }

    private void initDone() {
        initDone = true;
        setUI();
    }

    protected abstract void setUI();

    protected boolean isInitDone() {
        return initDone;
    }
}
