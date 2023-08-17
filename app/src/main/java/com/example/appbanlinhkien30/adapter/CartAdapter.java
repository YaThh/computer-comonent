package com.example.appbanlinhkien30.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.model.Cart;
import com.example.appbanlinhkien30.util.Convert;

import org.w3c.dom.Text;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<Cart> cartList;
    double overTotalPrice = 0;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.name.setText(cartList.get(position).getProductName());
        holder.price.setText(cartList.get(position).getProductPrice());
        holder.quantity.setText(cartList.get(position).getTotalQuantity());
        holder.totalPrice.setText(cartList.get(position).getTotalPrice());

        String totalPriceCurrency = Convert.convertCurrencyStringToNumber(cartList.get(position).getTotalPrice());
        overTotalPrice = overTotalPrice + Double.parseDouble(totalPriceCurrency);
        Intent i = new Intent("MyTotalAmount");
        i.putExtra("totalAmount", overTotalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity, totalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvCartProductName);
            price = itemView.findViewById(R.id.tvCartProductPrice);
            quantity = itemView.findViewById(R.id.tvCartProductQuantity);
            totalPrice = itemView.findViewById(R.id.tvCartProductTotalPrice);
        }
    }
}
