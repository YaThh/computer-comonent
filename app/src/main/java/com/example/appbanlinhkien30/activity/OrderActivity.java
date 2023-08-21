package com.example.appbanlinhkien30.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.model.Cart;
import com.example.appbanlinhkien30.model.Order;
import com.example.appbanlinhkien30.util.Convert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore db;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        btnBack = findViewById(R.id.btnOrderBack);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        List<Cart> cartList = (List<Cart>) getIntent().getSerializableExtra("itemList");

        if (cartList != null && cartList.size() > 0) {

            Order newOrder = new Order();
            newOrder.setPurchaseDate(new Date());
            newOrder.setCartList(cartList);
            newOrder.setUserId(auth.getCurrentUser().getUid());

            db.collection("Order").document()
                    .set(newOrder)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(OrderActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(OrderActivity.this, "Đặt hàng không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        }


    }
