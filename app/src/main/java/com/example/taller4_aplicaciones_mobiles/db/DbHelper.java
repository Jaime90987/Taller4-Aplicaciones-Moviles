package com.example.taller4_aplicaciones_mobiles.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tienda.db";
    private static final int DATABASE_VERSION = 22;
    public static final String DATABASE_TABLE_NAME = "Productos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "product_name";
    public static final String COLUMN_IMG = "product_img";
    public static final String COLUMN_DESC = "product_description";
    public static final String COLUMN_QUANTITY = "prodcut_quantity";
    public static final String COLUMN_PRICE = "prodcut_price";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_IMG + " TEXT," +
                COLUMN_DESC + " TEXT," +
                COLUMN_QUANTITY + " INTEGER," +
                COLUMN_PRICE + " DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(db);
    }

}
