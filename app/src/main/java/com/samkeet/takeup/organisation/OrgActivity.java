package com.samkeet.takeup.organisation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.samkeet.takeup.Constants;
import com.samkeet.takeup.R;
import com.samkeet.takeup.activities.LoginActivity;

public class OrgActivity extends AppCompatActivity implements OnMapReadyCallback {

    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    private Toolbar toolbar;

    public boolean doubleBackToExitPressedOnce = false;

    private static final String TAG = "GPSTRACKING";
    private static final int REQUEST_PERMISSION_CODE_START = 101;

    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000 * 1;
    private static final float LOCATION_DISTANCE = 1f;

    public double lat;
    public double lon;
    public double acc;

    public Location mLastLocation;

    public LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    public GoogleMap mMap;
    public Marker now;

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFab = (FloatingActionButton) findViewById(R.id.fab1);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNewActivity.class);
                startActivity(intent);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                //.withHeaderBackground(R.drawable.reva_headerp)
//                .withHeaderBackground(R.drawable.header)
                /// TODO: 19-Oct-16
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
//                .withDrawerLayout(R.layout.crossfade_councling_material_drawer)
                .withHasStableIds(true)
//                .withDrawerWidthDp(72)
//                .withGenerateMiniDrawer(false)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new ProfileDrawerItem().withEmail(Constants.SharedPreferenceData.getEMAIL()).withName(Constants.SharedPreferenceData.getNAME().toUpperCase()).withNameShown(true).withSelectable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.ic_home_black_24dp).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Add New Plant").withIcon(R.drawable.ic_add_circle_outline_black_24dp).withIdentifier(2),
                        new PrimaryDrawerItem().withName("My Fleet").withIcon(R.drawable.ic_map_black_24dp).withIdentifier(3),
                        new PrimaryDrawerItem().withName("Logout").withIcon(R.drawable.ic_logout_24dp).withIdentifier(4),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("About Us").withIcon(R.drawable.ic_about_24dp).withIdentifier(5),
                        new PrimaryDrawerItem().withName("Developer").withIcon(R.drawable.ic_android_black_24dp).withIdentifier(6)
                )
                // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1) {

                        }
                        if (drawerItem.getIdentifier() == 2) {
                            Intent intent = new Intent(getApplicationContext(), AddNewActivity.class);
                            startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 3) {

                        }
                        if (drawerItem.getIdentifier() == 4) {
                            logout();
                        }
                        if (drawerItem.getIdentifier() == 5) {

                        }
                        if (drawerItem.getIdentifier() == 6) {

                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .build();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_CODE_START);
            return;
        }
        startTracking();
    }

    public void startTracking() {

        int off = 0;
        try {
            off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (off == 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Please Enable GPS");
            builder.setMessage("We need you to enable GPS for high accuracy in tracking your location.");
            String positiveText = "Enable";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(onGPS);
                        }
                    });
            String negativeText = "Exit";
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            AlertDialog dialog = builder.create();
            // display dialog
            dialog.show();

        }
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_CODE_START) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startTracking();
            } else {
                Toast.makeText(getApplicationContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void logout() {
        Constants.SharedPreferenceData.clearData();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        if (Constants.LocationsData.lat != 0) {
            now = mMap.addMarker(new MarkerOptions().position(new LatLng(Constants.LocationsData.lat, Constants.LocationsData.lon)).zIndex(10f).draggable(false).title("Me"));
        } else {
            now = mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).zIndex(10f).draggable(false).title("Me"));
        }
    }

    private class LocationListener implements android.location.LocationListener {

        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {


            //mLastLocation = isBetterLocation(mLastLocation, location);
            if (location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                acc = location.getAccuracy();

                Constants.LocationsData.lat = lat;
                Constants.LocationsData.lon = lon;
                Constants.LocationsData.acc = acc;
                if (now != null) {
                    now.remove();

                }
                now = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).draggable(false).title("My Location"));
                // Showing the current location in Google Map
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));

                // Zoom in the Google Map
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

}
