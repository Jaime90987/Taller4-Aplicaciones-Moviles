package com.example.taller4_aplicaciones_mobiles;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.taller4_aplicaciones_mobiles.Entities.Product;
import com.example.taller4_aplicaciones_mobiles.db.DbProducts;

public class SeeProductActivity extends AppCompatActivity {

    TextView name, quantity, price;
    AppCompatImageView image;
    Product product;
    DbProducts dbProducts;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_product);
        initComponents();
        getProductInfo();
    }

    private void initComponents() {
        name = findViewById(R.id.name);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
    }

    private void getProductInfo() {
        Intent intent = getIntent();

        if (intent.hasExtra("ID")) {
            id = (int) intent.getIntExtra("ID", 1);
            showProduct();
        } else {
            alert();
        }
    }

    private void showProduct() {
        DbProducts dbProducts = new DbProducts(this);
        product = dbProducts.showProduct(id);

        name.setText(product.getName());
        quantity.setText(String.valueOf(product.getQuantity()));
        price.setText(String.valueOf(product.getPrice()));
    }

    private void alert() {
        new AlertDialog.Builder(this)
                .setMessage("Error al mostrar la información del Producto")
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> finish()).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            Intent intent = new Intent(this, EditProductActivity.class);
            intent.putExtra("ID", id);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.delete) {
            confirmDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Eliminar producto");
        builder.setMessage("¿Esta seguro de querer eliminar este producto?");
        builder.setPositiveButton("Si, eliminar", (dialogInterface, i) -> {
            dbProducts = new DbProducts(this);
            dbProducts.deleteProduct(id);
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        });
        builder.setNegativeButton("No, cancelar", (dialogInterface, i) -> {
        });
        builder.create().show();
    }

}