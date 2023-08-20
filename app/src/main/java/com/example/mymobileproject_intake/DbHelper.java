package com.example.mymobileproject_intake;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food_database.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성 쿼리문
        String SQL_CREATE_FOOD_TABLE = "CREATE TABLE " + FoodContract.FoodEntry.TABLE_NAME + " ("
                + FoodContract.FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodContract.FoodEntry.COLUMN_ONCE + " TEXT NOT NULL, "
                + FoodContract.FoodEntry.COLUMN_CABO + " TEXT NOT NULL, "
                + FoodContract.FoodEntry.COLUMN_FAT + " TEXT NOT NULL, "
                + FoodContract.FoodEntry.COLUMN_KCAL + " TEXT NOT NULL, "
                + FoodContract.FoodEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + FoodContract.FoodEntry.COLUMN_NATRIUM + " TEXT NOT NULL, "
                + FoodContract.FoodEntry.COLUMN_PROTEIN + " TEXT NOT NULL, "
                + FoodContract.FoodEntry.COLUMN_SUGAR + " TEXT NOT NULL);";

        // 테이블 생성
        db.execSQL(SQL_CREATE_FOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 버전이 변경되었을 때 수행할 작업
        // 현재는 단순히 테이블을 삭제하고 다시 생성하도록 함
        db.execSQL("DROP TABLE IF EXISTS " + FoodContract.FoodEntry.TABLE_NAME);
        onCreate(db);
    }
}