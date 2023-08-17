package com.example.appbanlinhkien30.activity.ui.cart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbanlinhkien30.adapter.CartAdapter;
import com.example.appbanlinhkien30.databinding.FragmentCartBinding;
import com.example.appbanlinhkien30.model.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
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
        cartRec = binding.recCart;
        cartRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext(), cartList);
        cartRec.setAdapter(cartAdapter);

        db.collection("Cart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                Cart cart = document.toObject(Cart.class);
                                cartList.add(cart);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}