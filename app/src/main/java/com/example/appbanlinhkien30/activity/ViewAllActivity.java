package com.example.appbanlinhkien30.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.adapter.ViewAllAdapter;
import com.example.appbanlinhkien30.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView viewAllRec;
    ViewAllAdapter viewAllAdapter;
    List<Product> productList;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        toolbar = findViewById(R.id.tbarProduct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String type = getIntent().getStringExtra("type");
        db = FirebaseFirestore.getInstance();

        viewAllRec = findViewById(R.id.recViewAll);
        viewAllRec.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, productList);
        viewAllRec.setAdapter(viewAllAdapter);

        //Lay VGA
        if (type != null && type.equalsIgnoreCase("VGA")) {
            db.collection("Product").whereEqualTo("Type", "VGA")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

          //Lay case
        if (type != null && type.equalsIgnoreCase("Case")) {
            db.collection("Product").whereEqualTo("Type", "Case")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

          //Lay RAM
        if (type != null && type.equalsIgnoreCase("RAM")) {
            db.collection("Product").whereEqualTo("Type", "RAM")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //Lay CPU
        if (type != null && type.equalsIgnoreCase("CPU")) {
            db.collection("Product").whereEqualTo("Type", "CPU")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //Lay HDD
        if (type != null && type.equalsIgnoreCase("HDD")) {
            db.collection("Product").whereEqualTo("Type", "HDD")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //Lay SSD
        if (type != null && type.equalsIgnoreCase("SSD")) {
            db.collection("Product").whereEqualTo("Type", "SSD")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //Lay PSU
        if (type != null && type.equalsIgnoreCase("PSU")) {
            db.collection("Product").whereEqualTo("Type", "PSU")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //Lay mainboard
        if (type != null && type.equalsIgnoreCase("Mainboard")) {
            db.collection("Product").whereEqualTo("Type", "Mainboard")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //Lay cooler
        if (type != null && type.equalsIgnoreCase("Cooler")) {
            db.collection("Product").whereEqualTo("Type", "Cooler")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }


    }
}