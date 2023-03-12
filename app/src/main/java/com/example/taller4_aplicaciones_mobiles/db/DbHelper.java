package com.example.taller4_aplicaciones_mobiles.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Tienda.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_NAME = "Productos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name_product";
    private static final String COLUMN_IMG = "img_product";
    private static final String COLUMN_QUANTITY = "quantity_prodcut";
    private static final String COLUMN_PRICE = "price_prodcut";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_IMG + " TEXT," +
                COLUMN_QUANTITY + " INTEGER," +
                COLUMN_PRICE + " DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(db);
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

}
