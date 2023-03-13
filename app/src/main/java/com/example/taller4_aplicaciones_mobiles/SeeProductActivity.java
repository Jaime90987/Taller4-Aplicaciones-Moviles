package com.example.taller4_aplicaciones_mobiles;

import android.annotation.SuppressLint;
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
import com.squareup.picasso.Picasso;

public class SeeProductActivity extends AppCompatActivity {

    TextView name, description, quantity, price;
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
        description = findViewById(R.id.description);
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showProduct() {
        DbProducts dbProducts = new DbProducts(this);
        product = dbProducts.showProduct(id);
        name.setText(product.getName());
        description.setText(product.getDescription());

        if (product.getQuantity() > 0)
            quantity.setText(String.format(getResources().getString(R.string.in_stock) + " " + product.getQuantity()));
        else
            quantity.setText(getResources().getString(R.string.out_stock));

        price.setText(String.format("$" + product.getPrice()));

        if (product.getImage() != null) {
            Picasso.get().load(product.getImage()).into(image);
        } else
            image.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_image, getTheme()));
    }

    private void alert() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.error_displaying_product_information))
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
        builder.setTitle(getResources().getString(R.string.delete_product));
        builder.setMessage(getResources().getString(R.string.are_you_sure_you_want_to_remove_this_product));
        builder.setPositiveButton(getResources().getString(R.string.yes_delete), (dialogInterface, i) -> {
            dbProducts = new DbProducts(this);
            dbProducts.deleteProduct(id);
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        });
        builder.setNegativeButton(getResources().getString(R.string.no_cancel), (dialogInterface, i) -> {
        });
        builder.create().show();
    }

}