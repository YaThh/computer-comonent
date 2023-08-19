package com.example.appbanlinhkien30.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.appbanlinhkien30.activity.ProductDetailActivity;
import com.example.appbanlinhkien30.model.Product;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private Context context;
    private List<Product> popularList;

    public PopularAdapter(Context context, List<Product> popularList) {
        this.context = context;
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(popularList.get(position).getImgUrl())
                .into(holder.popImg);
        holder.name.setText(popularList.get(position).getName());
        holder.description.setText(popularList.get(position).getDescription());
        holder.rating.setText(popularList.get(position).getRating());
        holder.discount.setText(popularList.get(position).getDiscount());
        holder.ratingBar.setRating(Float.parseFloat(popularList.get(position).getRating()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    Intent i = new Intent(context, ProductDetailActivity.class);
                    i.putExtra("detail", popularList.get(clickedPosition));
                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name, description, rating, discount;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImg = itemView.findViewById(R.id.imgPopular);
            name = itemView.findViewById(R.id.tvPopularName);
            description = itemView.findViewById(R.id.tvPopularDescription);
            rating = itemView.findViewById(R.id.tvPopularRating);
            discount = itemView.findViewById(R.id.tvPopularDiscount);
            ratingBar = itemView.findViewById(R.id.rbarPopular);
        }
    }
}
