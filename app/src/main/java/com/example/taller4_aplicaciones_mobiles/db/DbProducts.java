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
    ArrayList<Product> listProducts;

    public DbProducts(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public void addProduct(String name, String imagePath, String description, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_IMG, imagePath);
        values.put(COLUMN_DESC, description);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_PRICE, price);

        try {
            db.insert(DATABASE_TABLE_NAME, null, values);
        } catch (SQLException e) {
            Log.e("", "Error al insertar datos en la base de datos: " + e.getMessage());
            Toast.makeText(context, "Error al registrar el producto", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public ArrayList<Product> showAllProducts() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        listProducts = new ArrayList<>();
        Product product;
        Cursor cursorProducts;

        cursorProducts = db.rawQuery("SELECT * FROM " + DATABASE_TABLE_NAME, null);

        if (cursorProducts.moveToFirst()) {
            do {
                product = new Product();
                product.setId(cursorProducts.getInt(0));
                product.setName(cursorProducts.getString(1));
                product.setImage(cursorProducts.getString(2));
                product.setDescription(cursorProducts.getString(3));
                product.setQuantity(cursorProducts.getInt(4));
                product.setPrice(cursorProducts.getDouble(5));

                listProducts.add(product);
            } while (cursorProducts.moveToNext());
        }

        cursorProducts.close();

        return listProducts;
    }

    public Product showProduct(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Product product = null;
        Cursor cursorProducts;

        cursorProducts = db.rawQuery(" SELECT * FROM " + DATABASE_TABLE_NAME + " WHERE id = " + id + " LIMIT 1 ", null);

        if (cursorProducts.moveToFirst()) {
            product = new Product();
            product.setId(cursorProducts.getInt(0));
            product.setName(cursorProducts.getString(1));
            product.setImage(cursorProducts.getString(2));
            product.setDescription(cursorProducts.getString(3));
            product.setQuantity(cursorProducts.getInt(4));
            product.setPrice(cursorProducts.getDouble(5));
        }

        cursorProducts.close();

        return product;
    }

    public void updateProduct(int id, String name, String imagePath, String description, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_IMG, imagePath);
        values.put(COLUMN_DESC, description);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_PRICE, price);

        try {
            db.update(DATABASE_TABLE_NAME, values, " id  = ?", new String[]{String.valueOf(id)});
        } catch (SQLException e) {
            Log.e("", "Error al insertar datos en la base de datos: " + e.getMessage());
            Toast.makeText(context, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE FROM " + DATABASE_TABLE_NAME + " WHERE id = " + id);
    }

    public boolean isTableEmpty() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DATABASE_TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count == 0;
        }
        return true;
    }

    public void deleteAllProducts() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(" DELETE FROM " + DATABASE_TABLE_NAME);
    }
}
