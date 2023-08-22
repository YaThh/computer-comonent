package com.example.appbanlinhkien30.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.activity.ProductDetailActivity;
import com.example.appbanlinhkien30.activity.ui.admin.AdminProductDelFragment;
import com.example.appbanlinhkien30.activity.ui.home.HomeFragment;
import com.example.appbanlinhkien30.model.Product;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    Context context;
    List<Product> productList;

    public ViewAllAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_all, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(productList.get(position).getImgUrl()).into(holder.imgViewALl);
        holder.name.setText(productList.get(position).getName());
        holder.description.setText(productList.get(position).getDescription());
        holder.rating.setText(productList.get(position).getRating());
        holder.price.setText(productList.get(position).getPrice());
        holder.ratingBar.setRating(Float.parseFloat(productList.get(position).getRating()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    Product clickedProduct = productList.get(clickedPosition);

                    //Kiem tra context co phai la instance cua activity
                    if (context instanceof FragmentActivity) {
                        FragmentActivity fragmentActivity = (FragmentActivity) context;

                        //Kiem tra AdminProductDelFragment
                        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragContainer);
                        if (currentFragment instanceof AdminProductDelFragment) {
                            AdminProductDelFragment adminProductDelFragment = (AdminProductDelFragment) currentFragment;
                            adminProductDelFragment.setEditText(clickedProduct.getName());
                        } else {
                            Intent i = new Intent(context, ProductDetailActivity.class);
                            i.putExtra("detail", productList.get(clickedPosition));
                            context.startActivity(i);
                        }

                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgViewALl;
        TextView name, description, rating, price;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewALl = itemView.findViewById(R.id.imgViewAll);
            name = itemView.findViewById(R.id.tvViewAllName);
            description = itemView.findViewById(R.id.tvViewAllDescription);
            rating = itemView.findViewById(R.id.tvViewAllRating);
            price = itemView.findViewById(R.id.tvViewAllPrice);
            ratingBar = itemView.findViewById(R.id.rbarViewAll);
        }
    }

}
