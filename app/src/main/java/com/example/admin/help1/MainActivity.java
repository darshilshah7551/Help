package com.example.admin.help1;


import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Fragment cfragment = new ContactsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new MapFragment());



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.map_frag:
                Intent i = new Intent(this, MapsActivity.class);
                startActivity(i);
                break;

    /*        case R.id.contacts_frag:
                fragment = cfragment;

                break;
*/
            case R.id.profile_frag:
                fragment = new ProfileFragment();
                break;

            case R.id.settings_frag:
                fragment = new SettingsFragment();
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
}