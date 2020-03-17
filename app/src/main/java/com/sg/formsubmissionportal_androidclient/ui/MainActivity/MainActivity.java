package com.sg.formsubmissionportal_androidclient.ui.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.di.component.DaggerMainActivityComponent;
import com.sg.formsubmissionportal_androidclient.di.component.MainActivityComponent;
import com.sg.formsubmissionportal_androidclient.di.module.OtherNetworkServicesModule;
import com.sg.formsubmissionportal_androidclient.network.FormService;
import com.sg.formsubmissionportal_androidclient.network.RetrofitInstance;
import com.sg.formsubmissionportal_androidclient.network.UserService;
import com.sg.formsubmissionportal_androidclient.ui.LoginActivity;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Menu;
import android.widget.TextView;

import retrofit2.Retrofit;

import static com.sg.formsubmissionportal_androidclient.di.App.PREFER_NAME;
import static com.sg.formsubmissionportal_androidclient.di.App.PRIVATE_MODE;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public DrawerLayout drawerLayout;
    public NavController navController;
    public SwipeRefreshLayout swipeRefreshLayout;
    private NavigationView navigationView;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static String firstName;
    public static String lastName;
    public static String email;
    public static String role;
    public static String token;
    public static FormService formService;
    public static UserService userService;
    public static Long userid;
    private static MainActivityComponent component;
    private static Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref=getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        firstName=pref.getString("firstName","");
        lastName=pref.getString("lastName","");
        email=pref.getString("email","");
        role=pref.getString("role","");
        userid=pref.getLong("userid",0);
        token=pref.getString("token","");
        buildDaggerOtherComponent();
        editor=pref.edit();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_myprofile,R.id.nav_allforms,R.id.nav_myforms)
                .setDrawerLayout(drawerLayout)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Log.d("MainActivityDon123",pref.getString("email",""));
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayoutMain);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.DKGRAY, Color.RED,Color.GREEN,Color.MAGENTA,Color.BLACK,Color.CYAN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });
        View v=navigationView.getHeaderView(0);
        TextView name=v.findViewById(R.id.headerNameTV);
        TextView email=v.findViewById(R.id.headerEmailTV);
        name.setText(firstName+" "+lastName);
        email.setText(this.email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);

        }else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            editor.clear().apply();
            editor.clear().commit();
            editor.remove("firsName").commit();
            editor.remove("lastName").commit();
            editor.remove("email").commit();
            editor.remove("token").commit();
            editor.remove("userid").commit();
            editor.remove("role").commit();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            MainActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public static void buildDaggerOtherComponent(){
        component= DaggerMainActivityComponent.builder()
                .otherNetworkServicesModule(new OtherNetworkServicesModule(token))
                .build();
    }


    public static MainActivityComponent getComponent(){
        return component;
    }





    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setCheckable(true);

        drawerLayout.closeDrawers();

        int id = item.getItemId();

        switch (id){
            case R.id.nav_logout:
                break;
        }
        return true;
    }
     */
}
