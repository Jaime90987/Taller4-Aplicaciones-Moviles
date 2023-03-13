package com.example.taller4_aplicaciones_mobiles;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.taller4_aplicaciones_mobiles.Entities.Product;
import com.example.taller4_aplicaciones_mobiles.db.DbProducts;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class EditProductActivity extends AppCompatActivity {

    AppCompatImageView image;
    TextInputLayout ti_name, ti_description, ti_quantity, ti_price;
    Button btn_saveChanges;
    String product_name, imagePath, product_description, product_quantity, product_price;
    Product product;
    int id;
    DbProducts dbProducts;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    image.setImageURI(uri);
                    imagePath = uri.toString();
                    Log.i("Imagen", "Uri de la imagen: " + uri);
                } else {
                    Log.i("Imagen", "No seleccionado");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        initComponents();
        initListeners();
        getProductInfo();
    }

    private void initComponents() {
        image = findViewById(R.id.iv_ImgProductE);
        ti_name = findViewById(R.id.ti_ProductNameE);
        ti_description = findViewById(R.id.ti_ProductDescriptionE);
        ti_quantity = findViewById(R.id.ti_ProductQuantityE);
        ti_price = findViewById(R.id.ti_ProductPriceE);
        btn_saveChanges = findViewById(R.id.btn_saveChanges);
    }

    private void initListeners() {
        image.setOnClickListener(view1 -> pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()));
        btn_saveChanges.setOnClickListener(view -> validateInputs());
    }

    private void validateInputs() {
        product_name = Objects.requireNonNull(ti_name.getEditText()).getText().toString().trim();
        product_description = Objects.requireNonNull(ti_description.getEditText()).getText().toString().trim();
        product_quantity = Objects.requireNonNull(ti_quantity.getEditText()).getText().toString().trim();
        product_price = Objects.requireNonNull(ti_price.getEditText()).getText().toString().trim();

        if (product_name.isEmpty())
            ti_name.setHelperText(getResources().getString(R.string.enter_the_product_name));
        else
            ti_name.setHelperText("");

        if (product_description.isEmpty())
            ti_description.setHelperText(getResources().getString(R.string.enter_the_product_description));
        else
            ti_description.setHelperText("");

        if (product_quantity.isEmpty())
            ti_quantity.setHelperText(getResources().getString(R.string.enter_the_product_quantity));
        else
            ti_quantity.setHelperText("");

        if (product_price.isEmpty())
            ti_price.setHelperText(getResources().getString(R.string.enter_the_product_price));
        else
            ti_price.setHelperText("");

        if (!product_name.isEmpty() && !product_description.isEmpty() && !product_quantity.isEmpty() && !product_price.isEmpty()) {
            insertToDb();
            hideKeyboard();
            clearAllInputs();
            alert();
        }
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
        dbProducts = new DbProducts(this);
        product = dbProducts.showProduct(id);
        Objects.requireNonNull(ti_name.getEditText()).setText(product.getName());
        Objects.requireNonNull(ti_description.getEditText()).setText(product.getDescription());
        Objects.requireNonNull(ti_quantity.getEditText()).setText(String.valueOf(product.getQuantity()));
        Objects.requireNonNull(ti_price.getEditText()).setText(String.valueOf(product.getPrice()));

        imagePath = product.getImage();

        if (imagePath != null)
            Picasso.get().load(product.getImage()).into(image);
        else
            image.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_image, getTheme()));
    }

    private void insertToDb() {
        dbProducts.updateProduct(id, product_name, imagePath, product_description, Integer.parseInt(product_quantity), Double.parseDouble(product_price));
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btn_saveChanges.getWindowToken(), 0);
    }

    private void clearAllInputs() {
        Objects.requireNonNull(ti_name.getEditText()).setText("");
        Objects.requireNonNull(ti_description.getEditText()).setText("");
        Objects.requireNonNull(ti_quantity.getEditText()).setText("");
        Objects.requireNonNull(ti_price.getEditText()).setText("");
    }

    private void alert() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.product_successfully_updated))
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                    startActivity(new Intent(this, MainActivity.class));
                    finishAffinity();
                })
                .setCancelable(false)
                .show();
    }
}