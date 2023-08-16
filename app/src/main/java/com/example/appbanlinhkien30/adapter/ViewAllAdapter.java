package com.example.appbanlinhkien30.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanlinhkien30.R;
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
