package com.example.appbanlinhkien30.activity.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.activity.HomeActivity;
import com.example.appbanlinhkien30.databinding.FragmentOrderBinding;
import com.google.firebase.auth.FirebaseAuth;

public class OrderFragment extends Fragment {

    FragmentOrderBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}