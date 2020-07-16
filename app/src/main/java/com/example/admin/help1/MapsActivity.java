package com.example.admin.help1;

import android.Manifest;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final Object MY_REQUEST_INT = 177;
    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    ImageButton ib;
    Fragment cfragment = new ContactsFragment();
    ArrayList<String> c = new ArrayList<>();

    String msg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(MapsActivity.this);
        ib = findViewById(R.id.sos_btn);
        c = DataClass.InnerData.getNumber();


        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                                new String[]
                                        {Manifest.permission.SEND_SMS}
                                , (Integer) MY_REQUEST_INT);
                    }

                } else {

                    Intent sms = new Intent(getApplicationContext(), MapsActivity.class);
                    PendingIntent pin = PendingIntent.getActivity(getApplicationContext(), 0, sms, 0);
                    SmsManager smsManager = SmsManager.getDefault();
                    Log.e("ASDDDDDDDDASSSS: ", "" + c);
                    for (int i = 0; i < c.size(); i++) {
                        smsManager.sendTextMessage(c.get(i), null, "Alert from one of your contacts. Location is: " + msg, pin, null);
                    }

                    Toast.makeText(getApplicationContext(), "Alert Message Sent", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.map_frag:
                Intent i = new Intent(this, MapsActivity.class);
                startActivity(i);
                break;

            case R.id.contacts_frag:
                fragment = cfragment;
                ib.setVisibility(View.GONE);
                break;

            case R.id.profile_frag:
                fragment = new ProfileFragment();
                ib.setVisibility(View.GONE);
                break;

            case R.id.settings_frag:
                fragment = new SettingsFragment();
                ib.setVisibility(View.GONE);
                break;
        }

        return loadFragment(fragment);
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            int commit = getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS )!=PackageManager.PERMISSION_GRANTED)  {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.SEND_SMS}, (Integer) MY_REQUEST_INT);
            }
            return;
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        try {
            Geocoder geo = new Geocoder(MapsActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty()) {
                Toast.makeText(this, "WAITING", Toast.LENGTH_SHORT).show();
            } else {
                if (addresses.size() > 0) {
                    msg = addresses.get(0).getAddressLine(0);
                    //yourtextfieldname.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}