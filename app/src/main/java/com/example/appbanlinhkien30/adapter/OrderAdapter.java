package com.example.appbanlinhkien30.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.model.Cart;
import com.example.appbanlinhkien30.model.Order;
import com.example.appbanlinhkien30.util.Convert;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);


        //Tao chuoi de noi ten va gia cua san pham
        StringBuilder productString = new StringBuilder();
        for (Cart cart : order.getCartList()) {
            productString.append(cart.getProductName()).append(" x").append(cart.getTotalQuantity()).append(", ");
        }

        //Xoa dau phay va space o cuoi
        if (productString.length() > 0) {
            productString.delete(productString.length() - 2, productString.length());
        }

        holder.id.setText("Đơn hàng #" + order.getId());
        holder.date.setText("Ngày mua hàng: " + Convert.formatDate(order.getPurchaseDate()));
        holder.product.setText("Sản phẩm: " + productString.toString());
        holder.price.setText(String.valueOf("Tổng cộng: " + order.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, date, product, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tvOrderId);
            date = itemView.findViewById(R.id.tvOrderDate);
            product = itemView.findViewById(R.id.tvOrderProduct);
            price = itemView.findViewById(R.id.tvOrderPrice);
        }
    }
}
