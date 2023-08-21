package com.example.appbanlinhkien30.activity.ui.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.databinding.FragmentAdminUserBinding;
import com.example.appbanlinhkien30.util.Switch;


public class AdminUserFragment extends Fragment {
    FragmentAdminUserBinding binding;

    public AdminUserFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnAdminUserBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.btnUserDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch.switchFragment(getParentFragmentManager(), new AdminUserDelFragment());
            }
        });

        return root;
    }

}