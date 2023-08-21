package com.example.appbanlinhkien30.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.activity.ui.admin.AdminHomeFragment;
import com.example.appbanlinhkien30.activity.ui.admin.AdminProductFragment;
import com.example.appbanlinhkien30.activity.ui.admin.AdminUserFragment;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    Button btnLogoutAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnLogoutAdmin = (Button) findViewById(R.id.btnLogoutAdmin);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragContainer, new AdminHomeFragment())
                    .commit();
        }
        btnLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminActivity.this, HomeActivity.class));
                finish();
            }
        });


    }


}