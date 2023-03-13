package com.example.taller4_aplicaciones_mobiles;

import android.app.AlertDialog;
import android.content.ContentResolver;
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

import com.example.taller4_aplicaciones_mobiles.db.DbProducts;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class NewProductActivity extends AppCompatActivity {

    AppCompatImageView imgProduct = null;
    TextInputLayout ti_name, ti_description, ti_quantity, ti_price;
    Button btn_addProduct;
    String product_name, imagePath, product_description, product_quantity, product_price;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    imgProduct.setImageURI(uri);
                    imagePath = uri.toString();
                    Log.i("Imagen", "Uri de la imagen: " + uri);
                } else {
                    imagePath = null;
                    Log.i("Imagen", "No seleccionado");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        initComponents();
        initListeners();
    }

    private void initComponents() {
        imgProduct = findViewById(R.id.iv_ImgProduct);
        ti_name = findViewById(R.id.ti_ProductName);
        ti_description = findViewById(R.id.ti_ProductDescription);
        ti_quantity = findViewById(R.id.ti_ProductQuantity);
        ti_price = findViewById(R.id.ti_ProductPrice);
        btn_addProduct = findViewById(R.id.btn_addProduct);
    }

    private void initListeners() {
        imgProduct.setOnClickListener(view1 -> pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()));
        btn_addProduct.setOnClickListener(view2 -> validateInputs());
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

    private void insertToDb() {
        DbProducts dbProducts = new DbProducts(this);
        dbProducts.addProduct(product_name, imagePath, product_description, Integer.parseInt(product_quantity), Double.parseDouble(product_price));
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btn_addProduct.getWindowToken(), 0);
    }

    private void clearAllInputs() {
        Objects.requireNonNull(ti_name.getEditText()).setText("");
        Objects.requireNonNull(ti_description.getEditText()).setText("");
        Objects.requireNonNull(ti_quantity.getEditText()).setText("");
        Objects.requireNonNull(ti_price.getEditText()).setText("");
    }

    private void alert() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.product_successfully_registered))
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                    startActivity(new Intent(this, MainActivity.class));
                    finishAffinity();
                })
                .setCancelable(false)
                .show();
    }

}