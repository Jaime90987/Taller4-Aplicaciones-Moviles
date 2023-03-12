package com.example.taller4_aplicaciones_mobiles;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
    ArrayList<Product> listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initListeners();
    }

    private void initComponents() {
        recyclerView = findViewById(R.id.recyclerView);
        floatingButton = findViewById(R.id.floatingButton);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));

        DbProducts dbProducts = new DbProducts(this);
        listProducts = new ArrayList<>();
        ProductsAdapter adapter = new ProductsAdapter(dbProducts.showAllProducts());
        recyclerView.setAdapter(adapter);

    }

    private void initListeners() {
        floatingButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, NewProductActivity.class)));
    }

}