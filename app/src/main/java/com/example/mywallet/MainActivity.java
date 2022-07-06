package com.example.mywallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView botttomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        botttomNav = findViewById(R.id.bottom_navigation);
        botttomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            break;
                        case R.id.nav_add_transaction:
                            selectedFragment = new AddTransactionFragment();
                            break;
                        case R.id.nav_report:
                            selectedFragment = new ReportFragment();
                            break;
                        case R.id.nav_wallet:
                            selectedFragment = new WalletFragment();
                        default:
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };
    public void toWallet(View view){
        botttomNav.getMenu().getItem(1).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WalletFragment()).commit();
    }
    public void toHistory(View view){
        botttomNav.getMenu().getItem(3).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HistoryFragment()).commit();
    }
    public void toReport(View view){
        botttomNav.getMenu().getItem(4).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ReportFragment()).commit();
    }

}