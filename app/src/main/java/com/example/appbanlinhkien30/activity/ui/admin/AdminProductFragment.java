package com.example.appbanlinhkien30.activity.ui.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.databinding.FragmentAdminProductBinding;


public class AdminProductFragment extends Fragment {

    FragmentAdminProductBinding binding;

    public AdminProductFragment() {
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
        binding = FragmentAdminProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnAdminProductBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return root;
    }
}