package com.example.fabrice.gestionlivraison.activities;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fabrice.gestionlivraison.R;
import com.example.fabrice.gestionlivraison.fragments.MyDeliveriesFragment;
import com.example.fabrice.gestionlivraison.fragments.MyDeliveriesHistoryItemFragment;
import com.example.fabrice.gestionlivraison.fragments.MyDeliveriesItemFragment;
import com.example.fabrice.gestionlivraison.fragments.NewDeliveryFragment;
import com.example.fabrice.gestionlivraison.fragments.dummy.MyDeliveriesContent;
import com.example.fabrice.gestionlivraison.fragments.dummy.MyDeliveriesHistoryContent;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MyDeliveriesFragment.OnFragmentInteractionListener,
                                                                NewDeliveryFragment.OnFragmentInteractionListener, MyDeliveriesHistoryItemFragment.OnListFragmentInteractionListener,
                                                                MyDeliveriesItemFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFragments();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    private void setFragments() {
        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = MyDeliveriesFragment.class;
        try
        {
            fragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_new_delivery)
        {
            fragment = new NewDeliveryFragment();
        }
        else if (id == R.id.nav_my_deliveries)
        {
            fragment = new MyDeliveriesItemFragment();
        }
        else if (id == R.id.nav_my_deliveries_history)
        {
            fragment = new MyDeliveriesHistoryItemFragment();
        }
        else if (id == R.id.nav_my_informations)
        {

        }
        else if(id == R.id.nav_logout)
        {
            finishAndRemoveTask();
        }

        if(fragment != null)
        {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);

        // Set action bar title
        setTitle(item.getTitle());

        // Close the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(MyDeliveriesHistoryContent.DummyItem item) {

    }

    @Override
    public void onListFragmentInteraction(MyDeliveriesContent.DummyItem item) {

    }
}
