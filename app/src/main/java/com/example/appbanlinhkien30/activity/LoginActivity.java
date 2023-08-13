package com.example.appbanlinhkien30.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.model.User;
import com.example.appbanlinhkien30.util.FieldValidator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private com.rey.material.widget.CheckBox chkRemember;
    private Button btnLogin;
    private TextView tvRegister;
    private FirebaseAuth fAuth;
    private DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        edtEmail = (EditText) findViewById(R.id.edtLoginEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        chkRemember = (com.rey.material.widget.CheckBox) findViewById(R.id.chkRemember);
        btnLogin = (Button) findViewById(R.id.btnLoginMain);
        tvRegister = (TextView) findViewById(R.id.tvRegister);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FieldValidator.checkField(edtEmail) &&
                    FieldValidator.checkField(edtPassword)){
                    fAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    checkUserAccessLevel(authResult.getUser().getUid());

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    savingPreferences();
                }
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }
    public void checkUserAccessLevel(String uid) {
        DatabaseReference userRef = rootRef.child("User").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent i;
                if(snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user.getIsAdmin() == 0) {
                        i = new Intent(LoginActivity.this, MainActivity.class);
                    }
                    else {
                        i = new Intent(LoginActivity.this, AdminActivity.class);
                    }
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void savingPreferences() {
        SharedPreferences pref = this.getSharedPreferences("RememberPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String email = edtEmail.getText().toString();
        String pwd = edtPassword.getText().toString();
        Boolean checked = chkRemember.isChecked();

        if(!checked) {
            editor.clear();
        }
        else {
           editor.putString("email", email);
           editor.putString("pwd", pwd);
           editor.putBoolean("checked", true);
        }
        editor.apply();
    }

    private void restoringPreferences() {
        SharedPreferences pref = this.getSharedPreferences("RememberPref", MODE_PRIVATE);
        if (pref != null) {
            Boolean checked = pref.getBoolean("checked", false);
            if (checked) {
                String email = pref.getString("email", "");
                String pwd = pref.getString("pwd", "");
                edtEmail.setText(email);
                edtPassword.setText(pwd);
            }
            chkRemember.setChecked(checked);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        savingPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoringPreferences();
    }
}