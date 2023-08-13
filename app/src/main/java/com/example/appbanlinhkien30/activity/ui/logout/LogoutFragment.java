package com.example.appbanlinhkien30.activity.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appbanlinhkien30.databinding.FragmentLogoutBinding;
import com.example.appbanlinhkien30.databinding.FragmentOrderBinding;

public class LogoutFragment extends Fragment {

    FragmentLogoutBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}