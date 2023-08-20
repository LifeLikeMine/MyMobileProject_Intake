package com.example.mymobileproject_intake;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymobileproject_intake.databinding.FragmentDietBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DietFragment extends Fragment {
    private FragmentDietBinding binding;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersRef;
    double sum_kcal=0.0;
    double sum_protein=0.0;
    double sum_fat=0.0;
    double sum_cabo=0.0;
    double sum_sugar=0.0;
    double sum_natrium=0.0;
    private TextView kcal, cabo, fat, sug, nat, pro;
    ArrayList<FoodListData> foodDataset;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDietBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        usersRef = mDatabase.getReference().child("users");

        foodDataset = new ArrayList<FoodListData>();

        TextView text_kcal = binding.kcal;
        TextView text_protein = binding.protein;
        TextView text_fat = binding.fat;
        TextView text_cabo = binding.cabo;
        TextView text_sugar = binding.sugar;
        TextView text_natrium = binding.natrium;

        kcal = binding.kcalAvg;
        cabo = binding.caboAvg;
        sug = binding.sugarAvg;
        fat = binding.fatAvg;
        nat = binding.natriumAvg;
        pro = binding.proteinAvg;

        mRecyclerView = binding.recyclerView;

        mLayoutManager = new LinearLayoutManager(getContext());  // for general

        mRecyclerView.setLayoutManager(mLayoutManager);


        String[] projection = {
                FoodContract.FoodEntry._ID,
                FoodContract.FoodEntry.COLUMN_ONCE,
                FoodContract.FoodEntry.COLUMN_CABO,
                FoodContract.FoodEntry.COLUMN_FAT,
                FoodContract.FoodEntry.COLUMN_KCAL,
                FoodContract.FoodEntry.COLUMN_NAME,
                FoodContract.FoodEntry.COLUMN_NATRIUM,
                FoodContract.FoodEntry.COLUMN_PROTEIN,
                FoodContract.FoodEntry.COLUMN_SUGAR
        };

        readUserInfo();

        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(
                FoodContract.FoodEntry.TABLE_NAME,   // 테이블 이름
                projection,                         // 읽어올 컬럼들
                null,                               // WHERE 절
                null,                               // WHERE 절의 인자
                null,                               // GROUP BY 절
                null,                               // HAVING 절
                null                                // ORDER BY 절
        );

        ArrayList<FoodListData> foodList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // 컬럼 인덱스 가져오기
                int onceColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_ONCE);
                int caboColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_CABO);
                int fatColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FAT);
                int kcalColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_KCAL);
                int nameColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_NAME);
                int natriumColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_NATRIUM);
                int proteinColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_PROTEIN);
                int sugarColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_SUGAR);

                // 데이터 추출
                String once = cursor.getString(onceColumnIndex);
                String cabo = cursor.getString(caboColumnIndex);
                String fat = cursor.getString(fatColumnIndex);
                String kcal = cursor.getString(kcalColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String natrium = cursor.getString(natriumColumnIndex);
                String protein = cursor.getString(proteinColumnIndex);
                String sugar = cursor.getString(sugarColumnIndex);

                // FoodListData 객체 생성 및 데이터 설정
                FoodListData foodData = new FoodListData(once, cabo, fat, kcal, name, natrium, protein, sugar);

                // ArrayList에 추가
                foodList.add(foodData);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        sum_kcal=0.0;
        sum_protein=0.0;
        sum_fat=0.0;
        sum_cabo=0.0;
        sum_sugar=0.0;
        sum_natrium=0.0;

        for (int i = 0; i < foodList.size(); i++) {

            FoodListData food = foodList.get(i);

            Log.d(".MainActivity", food.getf_name());
            // 검색된 데이터를 리스트에 추가한다.
            sum_kcal+=Double.parseDouble(food.getf_kcal());
            sum_protein+=Double.parseDouble(food.getf_protein());
            sum_fat+=Double.parseDouble(food.getf_fat());
            sum_cabo+=Double.parseDouble(food.getf_cabo());
            sum_sugar+=Double.parseDouble(food.getf_sugar());
            sum_natrium+=Double.parseDouble(food.getf_natrium());
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        text_kcal.setText(decimalFormat.format(sum_kcal));
        text_protein.setText(decimalFormat.format(sum_protein));
        text_fat.setText(decimalFormat.format(sum_fat));
        text_cabo.setText(decimalFormat.format(sum_cabo));
        text_sugar.setText(decimalFormat.format(sum_sugar));
        text_natrium.setText(decimalFormat.format(sum_natrium));

        mAdapter = new MenuAdapter(foodList, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void readUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User userData = dataSnapshot.getValue(User.class);
                        if (userData != null) {
                            kcal.setText(userData.getKcal());
                            cabo.setText(userData.getCabo());
                            pro.setText(userData.getPro());
                            fat.setText(userData.getFat());
                            sug.setText(userData.getSug());
                            nat.setText(userData.getNat());
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("NotificationsFragment", "Failed to read user information", databaseError.toException());
                }
            });
        }
    }
}
