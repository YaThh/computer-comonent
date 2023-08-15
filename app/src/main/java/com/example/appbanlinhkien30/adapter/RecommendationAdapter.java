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
import com.example.appbanlinhkien30.model.Recommendation;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    Context context;
    List<Recommendation> recommendationList;

    public RecommendationAdapter(Context context, List<Recommendation> recommendationList) {
        this.context = context;
        this.recommendationList = recommendationList;
    }

    @NonNull
    @Override
    public RecommendationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recommend_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(recommendationList.get(position).getImgUrl())
                .into(holder.recImg);
        holder.name.setText(recommendationList.get(position).getName());
        holder.rating.setText(recommendationList.get(position).getRating());
        holder.price.setText(recommendationList.get(position).getPrice());
        holder.ratingBar.setRating(Float.parseFloat(recommendationList.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        return recommendationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recImg;
        TextView name, rating, price;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recImg = itemView.findViewById(R.id.imgRecommend);
            name = itemView.findViewById(R.id.tvRecommendName);
            rating = itemView.findViewById(R.id.tvRecommendRating);
            price = itemView.findViewById(R.id.tvRecommendPrice);
            ratingBar = itemView.findViewById(R.id.rtbarRecommend);
        }
    }
}
