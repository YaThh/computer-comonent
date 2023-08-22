package com.example.appbanlinhkien30.activity.ui.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.databinding.FragmentAdminUserSetAdminBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdminUserSetAdminFragment extends Fragment {

    FragmentAdminUserSetAdminBinding binding;
    FirebaseDatabase db;

    public AdminUserSetAdminFragment() {
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
        binding = FragmentAdminUserSetAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseDatabase.getInstance();

        binding.btnAdminUserSetAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtAdminUserSetAdmin.getText().toString();
                if (!email.isEmpty()) {
                    DatabaseReference userRef = db.getReference("User");
                    Query query = userRef.orderByChild("email").equalTo(email);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                //Lay user ref dau tien co email trong query
                                DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();

                                userSnapshot.getRef().child("isAdmin").setValue(1);
                                Toast.makeText(requireContext(), "Người dùng đã trở thành admin", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(requireContext(), "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(requireContext(), "Chưa nhập email", Toast.LENGTH_SHORT).show();
                }
                }
        });
        return root;
    }
}