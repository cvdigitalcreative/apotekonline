package com.nisa.penjulanan;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nisa.penjulanan.Boundary.Account;
import com.nisa.penjulanan.Boundary.Apoteker.AddItem;
import com.nisa.penjulanan.Boundary.Apoteker.ListOrder;
import com.nisa.penjulanan.Boundary.FinishOrderFrag;
import com.nisa.penjulanan.Boundary.Users.Checkout_item;
import com.nisa.penjulanan.Boundary.Users.Detail_Item;
import com.nisa.penjulanan.Boundary.Users.Homeuser;

public class MenuActivity extends AppCompatActivity {
    private ActionBar toolbar;
    private int getlogin;
    String admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        toolbar = getSupportActionBar();

        getlogin = getIntent().getExtras().getInt("keylogin");
        System.out.println("getlogin :" +getlogin);


        if (getlogin == 1){
            //Navbar
            BottomNavigationView navigationView = findViewById(R.id.menulab);
            navigationView.inflateMenu(R.menu.menu);
            navigationView.getMenu().removeItem(R.id.listorder);
            navigationView.getMenu().removeItem(R.id.add_item);
            navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

            // load the store fragment by default
            // toolbar.setTitle("Home");
            admin = "1";
            loadFragment(new Homeuser());
        }
            else {
            //Navbar
            BottomNavigationView navigationView = findViewById(R.id.menulab);
            navigationView.inflateMenu(R.menu.menu_admin);
            navigationView.getMenu().removeItem(R.id.checkout);
            navigationView.getMenu().removeItem(R.id.home_order);
            navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

            admin = "0";
            // load the store fragment by default
            // toolbar.setTitle("Home");
            loadFragment(new ListOrder());

        }
    }

    private void loadFragment(Fragment fragment) {
        //loadfragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment)
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home_order:
                    fragment = new Homeuser();
                    loadFragment(fragment);
                    return true;
                case R.id.checkout:
                    fragment = new Checkout_item();
                    loadFragment(fragment);
                    return true;
                case R.id.listorder:
                    fragment = new ListOrder();
                    loadFragment(fragment);
                    return true;
                case R.id.add_item:
                    fragment = new AddItem();
                    loadFragment(fragment);
                    return true;
                case R.id.progress_item:
                    Bundle bundle =  new Bundle();
                    bundle.putString("admin", admin);
                    fragment = new FinishOrderFrag();
                    fragment.setArguments(bundle);

                    loadFragment(fragment);

                    return true;
                case R.id.account_menu:
                    fragment = new Account();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    }
