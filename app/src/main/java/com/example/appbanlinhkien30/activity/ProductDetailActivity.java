package com.example.appbanlinhkien30.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.model.Product;
import com.example.appbanlinhkien30.util.Convert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgDetail;
    TextView price, rating, description, ratingCount, quantity, name;
    int totalQuantity = 1;
    double totalPrice;
    ImageView addItem, removeItem;
    Toolbar toolbar;
    RatingBar ratingBar;
    Button addToCart;
    Product product = null;
    FirebaseFirestore db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.tbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof Product) {
            product = (Product) object;
        }

        imgDetail = findViewById(R.id.imgDetail);
        name = findViewById(R.id.tvDetailName);
        price = findViewById(R.id.tvDetailPrice);
        rating = findViewById(R.id.tvDetailRating);
        ratingBar = findViewById(R.id.rbarDetailIndicator);
        description = findViewById(R.id.tvDetailDescription);
        ratingCount = findViewById(R.id.tvDetailRatingCount);
        addItem = findViewById(R.id.imgPlus);
        removeItem = findViewById(R.id.imgMinus);
        quantity = findViewById(R.id.tvQuantity);
        addToCart = findViewById(R.id.btnAddToCart);

        if (product != null) {
            Glide.with(getApplicationContext()).load(product.getImgUrl()).into(imgDetail);
            name.setText(product.getName());
            price.setText("Giá: " + product.getPrice());
            rating.setText(product.getRating());
            description.setText(product.getDescription());
            ratingBar.setRating(Float.parseFloat(product.getRating()));

        }


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity < 9) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity > 1)
                {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
    }

    private void addToCart() {

        String saveCurrentDate, saveCurrentTime;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currenDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currenDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        totalPrice = Double.parseDouble(Convert.convertCurrencyStringToNumber(product.getPrice()))  * totalQuantity;

        DocumentReference cartDocRef = db.collection("User").document(auth.getCurrentUser().getUid())
                .collection("Cart").document();


        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("ProductName", product.getName());
        cartItem.put("ProductPrice", product.getPrice());
        cartItem.put("CurrentDate", saveCurrentDate);
        cartItem.put("CurrentTime", saveCurrentTime);
        cartItem.put("TotalQuantity", quantity.getText());
        cartItem.put("TotalPrice", Convert.convertNumberToCurrencyString(totalPrice));

        cartDocRef.set(cartItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProductDetailActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Lỗi khi thêm vào giỏ hànhg", Toast.LENGTH_SHORT).show();
                }
            }
            });
        }
}