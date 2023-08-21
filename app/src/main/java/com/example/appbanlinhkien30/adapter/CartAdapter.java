package com.example.appbanlinhkien30.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.model.Cart;
import com.example.appbanlinhkien30.util.Convert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<Cart> cartList;
    double overTotalPrice = 0;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
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

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    double deletedItemPrice = Double.parseDouble(Convert.convertCurrencyStringToNumber(
                            cartList.get(clickedPosition).getTotalPrice()));
                    overTotalPrice -= deletedItemPrice;

                    DocumentReference cartDocRef = db.collection("User").document(auth.getCurrentUser().getUid())
                            .collection("Cart").document(cartList.get(clickedPosition).getId());
                    cartDocRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                cartList.remove(cartList.get(clickedPosition));
                                notifyDataSetChanged();

                                //Cap nhat gia sau khi xoa khoi gio hang
                                Intent i = new Intent("MyTotalAmount");
                                i.putExtra("totalAmount", overTotalPrice);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(i);
                            }
                        }
                    });
                }


            }
        });




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
        ImageView imgDelete;
        Button buy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvCartProductName);
            price = itemView.findViewById(R.id.tvCartProductPrice);
            quantity = itemView.findViewById(R.id.tvCartProductQuantity);
            totalPrice = itemView.findViewById(R.id.tvCartProductTotalPrice);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            buy = itemView.findViewById(R.id.btnBuy);
        }
    }
}
