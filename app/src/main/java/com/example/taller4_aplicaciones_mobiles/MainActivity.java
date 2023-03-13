package com.example.taller4_aplicaciones_mobiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.taller4_aplicaciones_mobiles.Adapters.ProductsAdapter;
import com.example.taller4_aplicaciones_mobiles.Entities.Product;
import com.example.taller4_aplicaciones_mobiles.db.DbProducts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingButton;
    DbProducts dbProducts;
    ProductsAdapter adapter;
    ArrayList<Product> listProduct;
    TextView tvNoProduct;
    AppCompatImageView ivNoProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initListeners();
        showProducts();
    }

    private void initComponents() {
        recyclerView = findViewById(R.id.recyclerView);
        floatingButton = findViewById(R.id.floatingButton);
        tvNoProduct = findViewById(R.id.tv_noProduct);
        ivNoProduct = findViewById(R.id.iv_noProduct);
    }

    private void showProducts() {
        dbProducts = new DbProducts(this);
        if (dbProducts.isTableEmpty()) {
            tvNoProduct.setVisibility(View.VISIBLE);
            ivNoProduct.setVisibility(View.VISIBLE);
        } else {
            tvNoProduct.setVisibility(View.GONE);
            ivNoProduct.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));

            listProduct = dbProducts.showAllProducts();
            adapter = new ProductsAdapter(listProduct);
            recyclerView.setAdapter(adapter);
        }
    }

    private void initListeners() {
        floatingButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, NewProductActivity.class)));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteAll) {
            confirmDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar productos");
        builder.setMessage("¿Esta seguro de querer eliminar todos los productos?");
        builder.setPositiveButton("Si, eliminar todos", (dialogInterface, i) -> {
            dbProducts.deleteAllProducts();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        builder.setNegativeButton("No, cancelar", (dialogInterface, i) -> {
        });
        builder.create().show();
    }

}