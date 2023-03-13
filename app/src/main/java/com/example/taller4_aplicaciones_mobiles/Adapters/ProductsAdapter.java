package com.example.taller4_aplicaciones_mobiles.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taller4_aplicaciones_mobiles.EditProductActivity;
import com.example.taller4_aplicaciones_mobiles.Entities.Product;
import com.example.taller4_aplicaciones_mobiles.MainActivity;
import com.example.taller4_aplicaciones_mobiles.R;
import com.example.taller4_aplicaciones_mobiles.SeeProductActivity;
import com.example.taller4_aplicaciones_mobiles.db.DbProducts;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    ArrayList<Product> listProducts;
    private BottomSheetDialog bottomSheetDialog, bottomSheetDialogDelete;

    public ProductsAdapter(ArrayList<Product> listProducts) {
        this.listProducts = listProducts;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, null, false);
        bottomSheetDialog = new BottomSheetDialog(parent.getContext());
        bottomSheetDialogDelete = new BottomSheetDialog(parent.getContext());
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

    @SuppressLint("NotifyDataSetChanged")
    public void updateProducts(ArrayList<Product> products) {
        listProducts = products;
        notifyDataSetChanged();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ProductnName, tv_ProductImg, tv_ProductnQuantity, tv_ProductPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_ProductnName = itemView.findViewById(R.id.tv_ProductnName);
            tv_ProductImg = itemView.findViewById(R.id.tv_ProductImg);
            tv_ProductnQuantity = itemView.findViewById(R.id.tv_ProductnQuantity);
            tv_ProductPrice = itemView.findViewById(R.id.tv_ProductPrice);

            itemView.setOnClickListener(view -> {
                Context contex = view.getContext();
                Intent intent = new Intent(contex, SeeProductActivity.class);
                intent.putExtra("ID", listProducts.get(getAdapterPosition()).getId());
                contex.startActivity(intent);
            });

            itemView.setOnLongClickListener(view -> {
                View bottomSheetView = LayoutInflater.from(view.getContext()).inflate(R.layout.modal_sheet, view.findViewById(R.id.standard_Bottom_Sheet));
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                bottomSheetView.findViewById(R.id.ly_editProduct).setOnClickListener(view1 -> {
                    bottomSheetDialog.dismiss();
                    Context contex = view1.getContext();
                    Intent intent = new Intent(contex, EditProductActivity.class);
                    intent.putExtra("ID", listProducts.get(getAdapterPosition()).getId());
                    contex.startActivity(intent);
                });

                bottomSheetView.findViewById(R.id.ly_deleteProduct).setOnClickListener(view1 -> {
                    bottomSheetDialog.dismiss();
                    View bottomSheetViewD = LayoutInflater.from(view.getContext()).inflate(R.layout.modal_sheet_delete, view1.findViewById(R.id.standard_Bottom_Sheet_Delete));
                    bottomSheetDialogDelete.setContentView(bottomSheetViewD);
                    bottomSheetDialogDelete.show();

                    bottomSheetViewD.findViewById(R.id.cancel).setOnClickListener(view2 -> bottomSheetDialogDelete.dismiss());

                    bottomSheetViewD.findViewById(R.id.delete).setOnClickListener(view3 -> {
                        bottomSheetDialogDelete.dismiss();
                        DbProducts dbProducts = new DbProducts(bottomSheetDialog.getContext());
                        dbProducts.deleteProduct(listProducts.get(getAdapterPosition()).getId());
                    });
                });

                return true;
            });
        }
    }
}
