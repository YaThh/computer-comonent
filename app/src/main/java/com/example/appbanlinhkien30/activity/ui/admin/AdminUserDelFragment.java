package com.example.appbanlinhkien30.activity.ui.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.databinding.FragmentAdminUserDelBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminUserDelFragment extends Fragment {

    FragmentAdminUserDelBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase db;

    public AdminUserDelFragment() {
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
        binding = FragmentAdminUserDelBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();


        binding.btnAdminUserDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtAdminUserDel.getText().toString();

                if (!email.isEmpty()) {
                    DatabaseReference userRef = db.getReference("User");
                    Query query = userRef.orderByChild("email").equalTo(email);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                //Xoa du lieu nguoi dung
                                userSnapshot.getRef().removeValue();
                                Toast.makeText(requireContext(), "Xoá người dùng thành công", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(requireContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(requireContext(), "Chưa nhập email", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.btnAdminUserDelBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return root;
    }
}