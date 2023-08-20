package com.example.mymobileproject_intake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.CustomViewHolder> {
    private ArrayList<FoodListData> foodList;
    private Context context;
    public static String day_text;

    public MenuAdapter(ArrayList<FoodListData> fooList, Context context) {
        this.foodList = fooList;
        this.context = context;
        this.day_text=day_text;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_c,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_name.setText(foodList.get(position).getf_name());
        holder.tv_once.setText(foodList.get(position).getf_once());
        holder.tv_kcal.setText(String.valueOf(foodList.get(position).getf_kcal()));
        holder.tv_protein.setText(String.valueOf(foodList.get(position).getf_protein()));
        holder.tv_fat.setText(String.valueOf(foodList.get(position).getf_fat()));
        holder.tv_cabo.setText(String.valueOf(foodList.get(position).getf_cabo()));
        holder.tv_sugar.setText(String.valueOf(foodList.get(position).getf_sugar()));
        holder.tv_natrium.setText(String.valueOf(foodList.get(position).getf_natrium()));
    }

    @Override
    public int getItemCount() {
        return (foodList!=null?foodList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_once;
        TextView tv_kcal;
        TextView tv_protein;
        TextView tv_fat;
        TextView tv_cabo;
        TextView tv_sugar;
        TextView tv_natrium;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_name=itemView.findViewById(R.id.tv_name);
            this.tv_once=itemView.findViewById(R.id.tv_once);
            this.tv_kcal=itemView.findViewById(R.id.tv_kcal);
            this.tv_protein=itemView.findViewById(R.id.tv_protein);
            this.tv_fat=itemView.findViewById(R.id.tv_fat);
            this.tv_cabo=itemView.findViewById(R.id.tv_cabo);
            this.tv_sugar=itemView.findViewById(R.id.tv_sugar);
            this.tv_natrium=itemView.findViewById(R.id.tv_natrium);

        }
    }

}