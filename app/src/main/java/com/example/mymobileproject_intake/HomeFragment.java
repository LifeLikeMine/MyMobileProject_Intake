package com.example.mymobileproject_intake;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymobileproject_intake.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText searchBtn;
    private Button btn1;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersRef;
    private OkHttpClient client;
    ArrayList<FoodListData> foodDataset;
    private TextView your;
    public static String key = "c55878e8208045e9a8f1";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        your = binding.textYourA;

        searchBtn = binding.searchBtn;

        btn1 = binding.button1;

        foodDataset = new ArrayList<FoodListData>();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        usersRef = mDatabase.getReference().child("users");

        mRecyclerView = binding.recyclerView;
        mRecyclerView.setVisibility(View.INVISIBLE);

        mLayoutManager = new LinearLayoutManager(getContext());  // for general

        mRecyclerView.setLayoutManager(mLayoutManager);

        client = new OkHttpClient();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtFood = searchBtn.getText().toString();
                reqFoodInfo(edtFood);
            }
        });

        readUserInfo();

        mAdapter = new MyAdapter(foodDataset, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void reqFoodInfo(String name){
        try {
            String url = "https://openapi.foodsafetykorea.go.kr/api/"
                    + key + "/I2790/json/1/10/DESC_KOR=" + name;

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "서버 응답이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String result = response.body().string();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONParser jsonParser = new JSONParser();
                                    JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                                    JSONObject service = (JSONObject) jsonObject.get("I2790");
                                    JSONArray foodCodeN = (JSONArray) service.get("row");

                                    foodDataset.clear();

                                    if (foodCodeN != null) {
                                        int limit = Math.min(foodCodeN.size(), 10); // JSON 길이와 10 중 작은 값을 사용

                                        for (int i = 0; i < limit; i++) {
                                            JSONObject foodCode = (JSONObject) foodCodeN.get(i);

                                            FoodListData food = new FoodListData();

                                            food.setf_name(foodCode.get("DESC_KOR").toString());
                                            food.setf_once(foodCode.get("SERVING_SIZE").toString());
                                            food.setf_kcal(foodCode.get("NUTR_CONT1").toString());
                                            food.setf_cabo(foodCode.get("NUTR_CONT2").toString());
                                            food.setf_protein(foodCode.get("NUTR_CONT3").toString());
                                            food.setf_fat(foodCode.get("NUTR_CONT4").toString());
                                            food.setf_sugar(foodCode.get("NUTR_CONT5").toString());
                                            food.setf_natrium(foodCode.get("NUTR_CONT6").toString());
                                            foodDataset.add(food);
                                        }
                                        Toast.makeText(getActivity(), "정보를 받아왔습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "데이터 없음!", Toast.LENGTH_SHORT).show();
                                    }
                                    mAdapter.notifyDataSetChanged();
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            your.setText(userData.getKcal());
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