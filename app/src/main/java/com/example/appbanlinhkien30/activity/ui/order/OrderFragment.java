package com.example.appbanlinhkien30.activity.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.activity.HomeActivity;
import com.example.appbanlinhkien30.adapter.CartAdapter;
import com.example.appbanlinhkien30.adapter.OrderAdapter;
import com.example.appbanlinhkien30.databinding.FragmentOrderBinding;
import com.example.appbanlinhkien30.model.Cart;
import com.example.appbanlinhkien30.model.Order;
import com.example.appbanlinhkien30.util.Convert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    FragmentOrderBinding binding;

    FirebaseAuth auth;
    FirebaseFirestore db;
    RecyclerView orderRec;
    OrderAdapter orderAdapter;
    List<Order> orderList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        orderRec = binding.recOrder;
        orderRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(getContext(), orderList);
        orderRec.setAdapter(orderAdapter);


        db.collection("Order").whereEqualTo("userId", auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            orderList.clear();
                            for (DocumentSnapshot document: task.getResult().getDocuments()) {
                                String documentId = document.getId();

                                Order order = document.toObject(Order.class);
                                order.setId(documentId);

                                double totalPrice = 0;
                                for (Cart cart : order.getCartList()) {
                                    totalPrice += Double.parseDouble(Convert.convertCurrencyStringToNumber(cart.getTotalPrice()));
                                }
                                order.setTotalPrice(Convert.convertNumberToCurrencyString(totalPrice));
                                db.collection("Order").document(documentId)
                                        .update("id", order.getUserId(), "totalPrice", order.getTotalPrice())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                                orderList.add(order);
                            }

                            if (orderList.isEmpty()) {
                                binding.orderRelative1.setVisibility(View.VISIBLE);
                                binding.orderRelative2.setVisibility(View.GONE);
                            } else {
                                binding.orderRelative1.setVisibility(View.GONE);
                                binding.orderRelative2.setVisibility(View.VISIBLE);
                                orderAdapter.notifyDataSetChanged();
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