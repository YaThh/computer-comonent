package com.example.appbanlinhkien30.activity.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanlinhkien30.adapter.HomeCategoryAdapter;
import com.example.appbanlinhkien30.adapter.PopularAdapter;
import com.example.appbanlinhkien30.adapter.RecommendationAdapter;
import com.example.appbanlinhkien30.databinding.FragmentHomeBinding;
import com.example.appbanlinhkien30.model.HomeCategory;
import com.example.appbanlinhkien30.model.Popular;
import com.example.appbanlinhkien30.model.Recommendation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    FirebaseFirestore db;
    List<Popular> popularList;
    List<HomeCategory> homeCategoryList;
    List<Recommendation> recommendList;
    PopularAdapter popularAdapter;
    HomeCategoryAdapter homeCategoryAdapter;
    RecommendationAdapter recommendAdapter;
    RecyclerView popularRec, homeCateRec, recommendRec;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance();

        View root = binding.getRoot();

        binding.pBarHome.setVisibility(View.VISIBLE);
        binding.svHome.setVisibility(View.GONE);

        //San pham noi bat
        popularRec = binding.recPopular;
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getActivity(), popularList);
        popularRec.setAdapter(popularAdapter);

        db.collection("PopularProduct").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Popular popularProduct = document.toObject(Popular.class);
                        popularList.add(popularProduct);
                        popularAdapter.notifyDataSetChanged();

                        binding.pBarHome.setVisibility(View.GONE);
                        binding.svHome.setVisibility(View.VISIBLE);

                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Danh muc san pham
        homeCateRec = binding.recExplore;
        homeCateRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeCategoryList = new ArrayList<>();
        homeCategoryAdapter = new HomeCategoryAdapter(getActivity(), homeCategoryList);
        homeCateRec.setAdapter(homeCategoryAdapter);

        db.collection("HomeCategory").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        HomeCategory category = document.toObject(HomeCategory.class);
                        homeCategoryList.add(category);
                        homeCategoryAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //De xuat
        recommendRec = binding.recRecommend;
        recommendRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommendList = new ArrayList<>();
        recommendAdapter = new RecommendationAdapter(getActivity(), recommendList);
        recommendRec.setAdapter(recommendAdapter);

        db.collection("Recommendation").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Recommendation recommendation = document.toObject(Recommendation.class);
                        recommendList.add(recommendation);
                        recommendAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}