package com.example.taller4_aplicaciones_mobiles.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.taller4_aplicaciones_mobiles.Entities.Product;

import java.util.ArrayList;

public class DbProducts extends DbHelper {

    Context context;

    public DbProducts(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public void addProduct(String name, String imagePath, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_IMG, imagePath);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_PRICE, price);

        try {
            db.insert(DATABASE_TABLE_NAME, null, values);
            Toast.makeText(context, "Producto registrado exitosamente", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Log.e("", "Error al insertar datos en la base de datos: " + e.getMessage());
            Toast.makeText(context, "Error al insertar datos en la base de datos", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public ArrayList<Product> showAllProducts() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Product> listProducts = new ArrayList<>();
        Product product;
        Cursor cursorProductos;

        cursorProductos = db.rawQuery("SELECT * FROM " + DATABASE_TABLE_NAME, null);

        if (cursorProductos.moveToFirst()) {
            do {
                product = new Product();
                product.setId(cursorProductos.getInt(0));
                product.setName(cursorProductos.getString(1));
                product.setImage(cursorProductos.getString(2));
                product.setQuantity(cursorProductos.getInt(3));
                product.setPrice(cursorProductos.getDouble(4));

                listProducts.add(product);
            } while (cursorProductos.moveToNext());
        }

        cursorProductos.close();

        return listProducts;
    }
}
