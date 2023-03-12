package com.example.taller4_aplicaciones_mobiles.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taller4_aplicaciones_mobiles.Entities.Product;
import com.example.taller4_aplicaciones_mobiles.MainActivity;
import com.example.taller4_aplicaciones_mobiles.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    static ArrayList<Product> listProducts;

    public ProductsAdapter(ArrayList<Product> listProducts) {
        ProductsAdapter.listProducts = listProducts;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, null, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.tv_ProductnName.setText(listProducts.get(position).getName());
        holder.tv_ProductImg.setText(listProducts.get(position).getImage());
        holder.tv_ProductnQuantity.setText(String.format("En Stock: " + listProducts.get(position).getQuantity()));
        holder.tv_ProductPrice.setText(String.format("$" + listProducts.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ProductnName, tv_ProductImg, tv_ProductnQuantity, tv_ProductPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_ProductnName = itemView.findViewById(R.id.tv_ProductnName);
            tv_ProductImg = itemView.findViewById(R.id.tv_ProductImg);
            tv_ProductnQuantity = itemView.findViewById(R.id.tv_ProductnQuantity);
            tv_ProductPrice = itemView.findViewById(R.id.tv_ProductPrice);

            itemView.setOnClickListener(view -> {
                Context contex = view.getContext();
                Intent intent = new Intent(contex, MainActivity.class);
                intent.putExtra("ID", listProducts.get(getAdapterPosition()).getId());
                contex.startActivity(intent);
            });
        }
    }
}
