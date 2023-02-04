package com.coder.ekantin.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.coder.ekantin.R;
import com.coder.ekantin.fragmentUser.akunUser;
import com.coder.ekantin.fragmentUser.historyUser;
import com.coder.ekantin.fragmentUser.homeUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardUser extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        this.dataInit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment;
            if(id == R.id.akun_menu){
                fragment = new akunUser();
            } else if(id == R.id.laundry_menu){
                fragment = new historyUser();
            } else {
                fragment = new homeUser();
            }

            return loadFragment(fragment);
        });
    }

    private void dataInit()
    {
        this.loadFragment(new homeUser());
        bottomNavigationView = findViewById(R.id.bottomMenu);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}