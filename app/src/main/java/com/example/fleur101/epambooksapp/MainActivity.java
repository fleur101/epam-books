package com.example.fleur101.epambooksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN_ACTIVITY_TAG";
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        toolbar.setTitle("epam reads");

        drawerLayout = findViewById(R.id.drawer_layout);

        Fragment fragment = new SearchFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    drawerLayout.closeDrawers();
                    int id = menuItem.getItemId();
                    Fragment fragment1 = null;
                    switch (id) {
                        case R.id.nav_search:
                            fragment1 = new SearchFragment();
                            break;
                        case R.id.nav_books:
                            fragment1 = new MyBooksFragment();
                            break;
                        case R.id.nav_requests:
                            fragment1 = new MyRequestsFragment();
                            break;
                        case R.id.nav_settings:
                            fragment1 = new ProfileFragment();
                            break;
                        case R.id.nav_logout:
                            signOut();
                            finish();
                            break;
                    }
                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here
                    if (fragment1 != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).commit();
                    }
                    return true;
                });

        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.id_token))
                .requestEmail()
                .build();
        GoogleSignIn.getClient(this, gso).signOut().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                Timber.e("Success");
            } else {
                Timber.e("Fail");
            }
        });
    }


}
