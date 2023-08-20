package com.example.mymobileproject_intake;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    EditText edEat;
    TextView tv_name, tv_once, tv_kcal, tv_protein, tv_fat, tv_cabo, tv_sugar, tv_natrium;
    Button btn;

    public MyViewHolder(View itemView) {
        super(itemView);

        edEat = (EditText) itemView.findViewById(R.id.ed_eat);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_once = (TextView) itemView.findViewById(R.id.tv_once);
        tv_kcal = (TextView) itemView.findViewById(R.id.kcal);
        tv_protein = (TextView) itemView.findViewById(R.id.protein);
        tv_fat = (TextView) itemView.findViewById(R.id.fat);
        tv_cabo = (TextView) itemView.findViewById(R.id.cabo);
        tv_sugar = (TextView) itemView.findViewById(R.id.sugar);
        tv_natrium = (TextView) itemView.findViewById(R.id.natrium);

        btn = (Button) itemView.findViewById(R.id.button2);
    }
}
