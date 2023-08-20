package com.example.mymobileproject_intake;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.EditText;

import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private ArrayList<FoodListData> foodList;
    private Context context;

    public MyAdapter(ArrayList<FoodListData> myDataset, Context ctx) {
        foodList = myDataset;
        context = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (this.foodList.size() != 0) {
            holder.tv_name.setText(foodList.get(position).getf_name());
            holder.tv_once.setText(foodList.get(position).getf_once());
            holder.tv_kcal.setText(String.valueOf(foodList.get(position).getf_kcal()));
            holder.tv_protein.setText(String.valueOf(foodList.get(position).getf_protein()));
            holder.tv_fat.setText(String.valueOf(foodList.get(position).getf_fat()));
            holder.tv_cabo.setText(String.valueOf(foodList.get(position).getf_cabo()));
            holder.tv_sugar.setText(String.valueOf(foodList.get(position).getf_sugar()));
            holder.tv_natrium.setText(String.valueOf(foodList.get(position).getf_natrium()));
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edEat = holder.itemView.findViewById(R.id.ed_eat);
                FoodListData foodItem = foodList.get(holder.getAdapterPosition());
                if(edEat.getText().toString() != "") {
                    double eat = Double.parseDouble(edEat.getText().toString());
                    foodItem.setEat(eat);
                }
                // SQLite 데이터베이스에 데이터 저장
                DbHelper dbHelper = new DbHelper(context);
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(FoodContract.FoodEntry.COLUMN_ONCE, foodItem.getf_once());
                values.put(FoodContract.FoodEntry.COLUMN_CABO, foodItem.getf_cabo());
                values.put(FoodContract.FoodEntry.COLUMN_FAT, foodItem.getf_fat());
                values.put(FoodContract.FoodEntry.COLUMN_KCAL, foodItem.getf_kcal());
                values.put(FoodContract.FoodEntry.COLUMN_NAME, foodItem.getf_name());
                values.put(FoodContract.FoodEntry.COLUMN_NATRIUM, foodItem.getf_natrium());
                values.put(FoodContract.FoodEntry.COLUMN_PROTEIN, foodItem.getf_protein());
                values.put(FoodContract.FoodEntry.COLUMN_SUGAR, foodItem.getf_sugar());
                long newRowId = database.insert(FoodContract.FoodEntry.TABLE_NAME, null, values);

                if (newRowId != -1) {
                    Toast.makeText(context.getApplicationContext(), "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context.getApplicationContext(), "데이터 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
