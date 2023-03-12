package com.example.taller4_aplicaciones_mobiles;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingButton;

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
    }

    private void initListeners() {
        floatingButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, NewProductActivity.class)));
    }

}