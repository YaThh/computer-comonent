package com.example.appbanlinhkien30.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.model.User;
import com.example.appbanlinhkien30.util.FieldValidator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtPhone, edtPassword, edtEmail;
    private Button btnRegister;
    private Dialog registerProgress;
    private FirebaseAuth fAuth;
    private DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtPassword = (EditText) findViewById(R.id.edtPasswordRegister);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnRegister = (Button) findViewById(R.id.btnCreateAccount);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FieldValidator.checkField(edtPhone)
                        && FieldValidator.checkField(edtEmail)
                        && FieldValidator.checkField(edtPassword)){
                    CreateAccount();
                }
            }
        });
    }

    private void CreateAccount() {
       String phone = edtPhone.getText().toString();
       String email = edtEmail.getText().toString();
       String pwd = edtPassword.getText().toString();

       registerProgress = new Dialog(RegisterActivity.this);
       registerProgress.setContentView(R.layout.dialog_register);
       registerProgress.setCancelable(false);
       registerProgress.show();

       //Tạo tài khoản với email và password
       fAuth.createUserWithEmailAndPassword(email, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
           @Override
           public void onSuccess(AuthResult authResult) {
               registerProgress.dismiss();
               FirebaseUser user = fAuth.getCurrentUser();

               User userdata = new User(phone, email, pwd, 0);
               rootRef.child("User").child(user.getUid()).setValue(userdata); //Lấy uid vừa tạo ở fAuth gán thành id cho User ở db

               Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();

               Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
               startActivity(i);
               finish();
               fAuth.signOut();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               registerProgress.dismiss();
               Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }
}