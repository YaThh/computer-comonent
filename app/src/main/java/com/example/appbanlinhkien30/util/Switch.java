package com.example.appbanlinhkien30.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.appbanlinhkien30.R;

public class Switch extends Fragment{
    public static void switchFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragContainer, fragment)
                .addToBackStack(null)
                .commit();
        }

}
