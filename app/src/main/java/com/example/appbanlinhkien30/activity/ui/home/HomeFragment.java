package com.example.appbanlinhkien30.activity.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.activity.HomeActivity;
import com.example.appbanlinhkien30.activity.MainActivity;
import com.example.appbanlinhkien30.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    private Button btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnLogout = (Button) root.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(requireActivity(), HomeActivity.class));
                getActivity().finish();
            }
        });
        return root;
    }

}