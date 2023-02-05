package com.coder.ekantin.merchant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.coder.ekantin.R;
import com.coder.ekantin.fragmentMerchant.akunMerchant;
import com.coder.ekantin.fragmentMerchant.homeMerchant;
import com.coder.ekantin.fragmentMerchant.menuMerchant;
import com.coder.ekantin.fragmentUser.akunUser;
import com.coder.ekantin.fragmentUser.historyUser;
import com.coder.ekantin.fragmentUser.homeUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardMerchant extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_merchant);
        this.dataInit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment;
            if(id == R.id.akun_menu){
                fragment = new akunMerchant();
            } else if(id == R.id.makanan_menu){
                fragment = new menuMerchant();
            } else {
                fragment = new homeMerchant();
            }

            return loadFragment(fragment);
        });
    }

    private void dataInit()
    {
        this.loadFragment(new homeMerchant());
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