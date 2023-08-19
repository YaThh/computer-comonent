package com.example.appbanlinhkien30.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.activity.ui.profile.ProfileUpdateListener;
import com.example.appbanlinhkien30.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanlinhkien30.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements ProfileUpdateListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Dialog logoutProgress;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        db = FirebaseDatabase.getInstance();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_category, R.id.nav_cart
                , R.id.nav_order, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Import anh va username tu db cho nav header
        View headerView = navigationView.getHeaderView(0);
        CircleImageView headerImg = headerView.findViewById(R.id.imgNavProfile);
        TextView headerName = headerView.findViewById(R.id.tvNavUsername);
        TextView headerEmail = headerView.findViewById(R.id.tvNavEmail);
        db.getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        Glide.with(MainActivity.this).load(user.getProfileImg()).into(headerImg);
                        headerName.setText(user.getUserName());
                        headerEmail.setText(user.getEmail());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //Su kien khi click vao logout tren nav
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_logout) {
                    logoutProgress = new Dialog(MainActivity.this);
                    logoutProgress.setContentView(R.layout.dialog_logout);
                    logoutProgress.setCancelable(false);
                    logoutProgress.show();

                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                    logoutProgress.dismiss();
                    return true;
                }
                NavigationUI.onNavDestinationSelected(item, navController);
                drawer.close();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public void onProfileNameUpdated(String userName) {
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        TextView headerName = headerView.findViewById(R.id.tvNavUsername);
        headerName.setText(userName);
    }

    @Override
    public void onProfieImageUpdated(String profileImageUrl) {
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        CircleImageView headerImg = headerView.findViewById(R.id.imgNavProfile);
        Glide.with(MainActivity.this).load(profileImageUrl).into(headerImg);
    }
}