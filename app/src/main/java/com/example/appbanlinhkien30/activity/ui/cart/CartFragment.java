package com.example.appbanlinhkien30.activity.ui.cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbanlinhkien30.activity.OrderActivity;
import com.example.appbanlinhkien30.adapter.CartAdapter;
import com.example.appbanlinhkien30.databinding.FragmentCartBinding;
import com.example.appbanlinhkien30.model.Cart;
import com.example.appbanlinhkien30.util.Convert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CartFragment extends Fragment {

    private FragmentCartBinding binding;

    FirebaseAuth auth;
    FirebaseFirestore db;
    RecyclerView cartRec;
    CartAdapter cartAdapter;
    List<Cart> cartList;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        cartRec = binding.recCart;
        cartRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext(), cartList);
        cartRec.setAdapter(cartAdapter);

        db.collection("User").document(auth.getCurrentUser().getUid())
                .collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cartList.clear();
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {

                                String documentId = document.getId();

                                Cart cart = document.toObject(Cart.class);
                                cart.setId(documentId);
                                cartList.add(cart);
                            }

                            if (cartList.isEmpty()) {
                                binding.cartRelative1.setVisibility(View.VISIBLE);
                                binding.cartRelative2.setVisibility(View.GONE);
                            } else {
                                binding.cartRelative1.setVisibility(View.GONE);
                                binding.cartRelative2.setVisibility(View.VISIBLE);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllCart(); 

                Intent i = new Intent(getContext(), OrderActivity.class);
                i.putExtra("itemList", (Serializable) cartList);
                startActivity(i);

            }
        });
        return root;
    }

    private void deleteAllCart() {
        CollectionReference cartCollectionRef = db.collection("User")
                .document(auth.getCurrentUser().getUid())
                .collection("Cart");

        cartCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        document.getReference().delete();
                    }
                }
            }
        });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double totalBill = intent.getDoubleExtra("totalAmount", 0);
            binding.tvCartTotalPrice.setText("Tổng tiền: " + Convert.convertNumberToCurrencyString(totalBill));
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        binding = null;
    }
}