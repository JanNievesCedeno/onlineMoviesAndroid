package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.databinding.ActivityAdminBinding;
import com.example.movieproject.databinding.ActivityMainBinding;
import com.example.movieproject.fragments.movieList;
import com.google.android.material.navigation.NavigationView;

public class Admin extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAdminBinding binding;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarAdmin.toolbarAdmin);

        DrawerLayout drawer = binding.drawerLayoutAdmin;
        NavigationView navigationView = binding.navViewAdmin;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.tableUser, R.id.tableMovies, R.id.tableSales)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerViewAdmin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            String userId = bundle.getString("userId");
            Intent movieList = new Intent(Admin.this, com.example.movieproject.fragments.movieList.class);
            movieList.putExtras(bundle);
            startActivity(movieList);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerViewAdmin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        recreate();

    }

    public void logOut(MenuItem item) {

        finish();
        Intent intentLogout = new Intent(this, Login.class);
        startActivity(intentLogout);
    }

}