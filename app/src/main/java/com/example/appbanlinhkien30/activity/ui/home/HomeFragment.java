package com.example.appbanlinhkien30.activity.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.activity.ViewAllActivity;
import com.example.appbanlinhkien30.activity.ui.category.CategoryFragment;
import com.example.appbanlinhkien30.adapter.HomeCategoryAdapter;
import com.example.appbanlinhkien30.adapter.PopularAdapter;
import com.example.appbanlinhkien30.adapter.RecommendationAdapter;
import com.example.appbanlinhkien30.adapter.ViewAllAdapter;
import com.example.appbanlinhkien30.databinding.FragmentHomeBinding;
import com.example.appbanlinhkien30.model.HomeCategory;
import com.example.appbanlinhkien30.model.Product;
import com.example.appbanlinhkien30.model.Recommendation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    FirebaseFirestore db;
    List<Product> popularList;
    List<HomeCategory> homeCategoryList;
    List<Recommendation> recommendList;
    PopularAdapter popularAdapter;
    HomeCategoryAdapter homeCategoryAdapter;
    RecommendationAdapter recommendAdapter;
    RecyclerView popularRec, homeCateRec, recommendRec;

    private List<Product> productList;
    private RecyclerView searchRec;
    private ViewAllAdapter viewAllAdapter;

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

        db.collection("Product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product popularProduct = document.toObject(Product.class);
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

        binding.tvShowMoreExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.action_home_category);
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

        //Tim san pham
        searchRec = binding.recSearch;
        productList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(), productList);
        searchRec.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRec.setAdapter(viewAllAdapter);
        searchRec.setHasFixedSize(true);
        binding.edtSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString().trim();
                productList.clear();
                if (!input.isEmpty()) {
                    searchProduct(input);
                }
            }
        });


        return root;
    }

    private void searchProduct(String input) {
        if (!input.isEmpty()) {
            db.collection("Product")
                    .whereGreaterThanOrEqualTo("name", input)
                    .whereLessThanOrEqualTo("name", input + "\uf8ff")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                productList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot document :task.getResult().getDocuments()) {
                                    Product product = document.toObject(Product.class);
                                    productList.add(product);
                                    viewAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}