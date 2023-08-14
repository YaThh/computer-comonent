package com.example.appbanlinhkien30.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.model.HomeCategory;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    List<HomeCategory> homeCategoryList;

    public HomeAdapter(Context context, List<HomeCategory> homeCategoryList) {
        this.context = context;
        this.homeCategoryList = homeCategoryList;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cate_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(homeCategoryList.get(position).getImgUrl())
                .into(holder.imgCate);
        holder.name.setText(homeCategoryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return homeCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCate;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCate = itemView.findViewById(R.id.imgCateHome);
            name = itemView.findViewById(R.id.tvCateHome);
        }
    }
}
