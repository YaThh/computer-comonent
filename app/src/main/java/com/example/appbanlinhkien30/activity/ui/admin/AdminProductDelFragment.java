package com.example.appbanlinhkien30.activity.ui.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.adapter.ViewAllAdapter;
import com.example.appbanlinhkien30.databinding.FragmentAdminProductDelBinding;
import com.example.appbanlinhkien30.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AdminProductDelFragment extends Fragment {

    FragmentAdminProductDelBinding binding;
    RecyclerView searchRec;
    List<Product> productList;
    ViewAllAdapter viewAllAdapter;
    FirebaseFirestore db;

    public AdminProductDelFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminProductDelBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();

        binding.btnAdminProductDelBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        searchRec = binding.recSearch;
        productList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(), productList);
        searchRec.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRec.setAdapter(viewAllAdapter);
        searchRec.setHasFixedSize(true);

        binding.edtAdminProductDel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString().trim();
                productList.clear();
                if (!input.isEmpty()) {
                    searchProduct(input);
                }
            }
        });
        binding.btnAdminProdDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.edtAdminProductDel.getText().toString();

                if (!name.isEmpty()) {
                    db.collection("Product")
                            .whereEqualTo("name", name)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && task.getResult() != null) {
                                        for (DocumentSnapshot document: task.getResult()) {
                                            DocumentReference docRef = document.getReference();
                                            docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(getContext(), "Xoá sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getContext(), "Xoá sản phẩm không thành công", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void searchProduct(String input) {
        if (!input.isEmpty()) {
            db.collection("Product")
                    .whereGreaterThanOrEqualTo("name", input)
                    .whereLessThanOrEqualTo("name", input + "\uf8ff")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                productList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    Product product = document.toObject(Product.class);
                                    productList.add(product);
                                    viewAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }

    }
     public void setEditText(String productName) {
        if (binding != null) {
            binding.edtAdminProductDel.setText(productName);
        }
    }
}