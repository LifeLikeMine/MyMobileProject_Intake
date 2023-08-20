package com.example.mymobileproject_intake;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mymobileproject_intake.databinding.FragmentUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersRef;
    private TextView name, sex, tall, age, weight, bmi, avg;
    private Button logout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        name = binding.textName;
        sex = binding.textSex;
        tall = binding.textTall;
        age = binding.textAge;
        weight = binding.textWeight;
        bmi = binding.textBmi;
        avg = binding.textAvg;

        logout = binding.btnLogout;

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        usersRef = mDatabase.getReference().child("users");

        readUserInfo();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그아웃
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

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
                            name.setText(userData.getName()+" 님의 정보");
                            sex.setText(userData.getSex());
                            tall.setText(userData.getTall()+"CM");
                            int birthYear = Integer.valueOf(userData.getBirth().substring(0, 4));
                            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                            int year = currentYear - birthYear + 1;
                            age.setText(String.valueOf(year)+"세");
                            weight.setText(userData.getWeight()+"KG");
                            double kg = Double.valueOf(userData.getWeight());
                            double cm = Double.valueOf(userData.getTall());
                            double m = cm / 100;
                            bmi.setText(String.format("%.2f", kg / (m * m)));
                            double height = Double.parseDouble(userData.getTall());
                            double aver;
                            if(userData.getSex() == "남성"){
                                aver = height * height * 22 / 10000;
                            } else {
                                aver = height * height * 21 / 10000;
                            }
                            avg.setText(String.format("%.1f"+"kg",aver));
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