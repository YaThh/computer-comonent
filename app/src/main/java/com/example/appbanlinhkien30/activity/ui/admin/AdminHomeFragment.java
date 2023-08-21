package com.example.appbanlinhkien30.activity.ui.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.databinding.FragmentAdminHomeBinding;
import com.example.appbanlinhkien30.util.Switch;

public class AdminHomeFragment extends Fragment {

    FragmentAdminHomeBinding binding;

    public AdminHomeFragment() {
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
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnUserAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch.switchFragment(getParentFragmentManager(),new AdminUserFragment());
            }
        });

        binding.btnProductAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch.switchFragment(getParentFragmentManager(),new AdminProductFragment());
            }
        });

        return root;
    }


}