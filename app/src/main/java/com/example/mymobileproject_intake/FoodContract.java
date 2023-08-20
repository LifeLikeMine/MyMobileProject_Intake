package com.example.mymobileproject_intake;

import android.provider.BaseColumns;

public final class FoodContract {
    private FoodContract() {}

    public static class FoodEntry implements BaseColumns {
        public static final String TABLE_NAME = "food";
        public static final String COLUMN_ONCE = "once";
        public static final String COLUMN_CABO = "cabo";
        public static final String COLUMN_FAT = "fat";
        public static final String COLUMN_KCAL = "kcal";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NATRIUM = "natrium";
        public static final String COLUMN_PROTEIN = "protein";
        public static final String COLUMN_SUGAR = "sugar";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_ONCE + " TEXT," +
                        COLUMN_CABO + " TEXT," +
                        COLUMN_FAT + " TEXT," +
                        COLUMN_KCAL + " TEXT," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_NATRIUM + " TEXT," +
                        COLUMN_PROTEIN + " TEXT," +
                        COLUMN_SUGAR + " TEXT)";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
