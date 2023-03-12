package com.example.taller4_aplicaciones_mobiles;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.taller4_aplicaciones_mobiles.db.DbHelper;
import com.example.taller4_aplicaciones_mobiles.db.DbProducts;
import com.google.android.material.textfield.TextInputLayout;

public class NewProductActivity extends AppCompatActivity {

    AppCompatImageView imgProduct;
    TextInputLayout ti_name, ti_quantity, ti_price;
    Button btn_addProduct;
    String product_name, product_quantity, product_price;

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
        ti_quantity = findViewById(R.id.ti_ProductQuantity);
        ti_price = findViewById(R.id.ti_ProductPrice);
        btn_addProduct = findViewById(R.id.btn_addProduct);
    }

    private void initListeners() {
        btn_addProduct.setOnClickListener(view -> validateInputs());
    }

    private void validateInputs() {
        product_name = ti_name.getEditText().getText().toString().trim();
        product_quantity = ti_quantity.getEditText().getText().toString().trim();
        product_price = ti_price.getEditText().getText().toString().trim();

        if (product_name.isEmpty())
            ti_name.setHelperText("Introduce el nombre del producto *");
        else
            ti_name.setHelperText("");

        if (product_quantity.isEmpty())
            ti_quantity.setHelperText("Introduce la cantidad del producto *");
        else
            ti_quantity.setHelperText("");

        if (product_price.isEmpty())
            ti_price.setHelperText("Introduce el precio del producto *");
        else
            ti_price.setHelperText("");

        if (!product_name.isEmpty() && !product_quantity.isEmpty() && !product_price.isEmpty()) {
            insertToDb();
            hideKeyboard();
            clearAllInputs();
        }
    }

    private void insertToDb() {
        DbProducts dbProducts = new DbProducts(this);
        dbProducts.addProduct(product_name, "", Integer.parseInt(product_quantity), Double.parseDouble(product_price));
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btn_addProduct.getWindowToken(), 0);
    }

    private void clearAllInputs() {
        ti_name.getEditText().setText("");
        ti_quantity.getEditText().setText("");
        ti_price.getEditText().setText("");
    }

}