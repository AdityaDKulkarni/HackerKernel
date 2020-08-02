package com.hackerkernel.androidtask.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.hackerkernel.androidtask.R;
import com.hackerkernel.androidtask.session.SessionManager;
import com.hackerkernel.androidtask.util.Constants;

public class NavigationActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private NavigationView navigationView;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawerLayout;
    private TextView tvUserName, tvUserEmail;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation2);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(getString(R.string.hackerkernel));

        sessionManager = SessionManager.getInstance(this);

        navigationView = findViewById(R.id.navigation_view);
        navController = Navigation.findNavController(this, R.id.host_fragment);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationUI.setupWithNavController(navigationView, navController);

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        displayUser();
    }

    private void displayUser(){
        tvUserName = navigationView.getHeaderView(0).findViewById(R.id.tv_user_name);
        tvUserEmail = navigationView.getHeaderView(0).findViewById(R.id.tv_user_email);
        tvUserName.setText(sessionManager.getSession().get(Constants.NAME));
        tvUserEmail.setText(sessionManager.getSession().get(Constants.EMAIL));

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isOpen()){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            finishAffinity();
        }
    }
}