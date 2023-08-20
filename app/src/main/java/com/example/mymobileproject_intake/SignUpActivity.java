package com.example.mymobileproject_intake;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText Email, Pw, name, tall, weight, birth;
    private Button mBtnReg;
    private RadioGroup radioGroup;
    private String strsex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        Email = findViewById(R.id.et_email);
        Pw = findViewById(R.id.et_pw2);
        name = findViewById(R.id.et_name);

        birth = findViewById(R.id.et_birth);
        weight = findViewById(R.id.et_weight);
        tall = findViewById(R.id.et_tall);
        mBtnReg = findViewById(R.id.btm_reg);

        mBtnReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String strEmail = Email.getText().toString();
                String strPwd = Pw.getText().toString();
                String strname = name.getText().toString();
                String strtall = tall.getText().toString();
                String strweight = weight.getText().toString();
                String strbirth = birth.getText().toString();
                //파이어베이스 어스 인증 처리
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                            String userId = firebaseUser.getUid();
                            User account = new User();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            account.setName(strname);
                            account.setTall(strtall);
                            account.setWeight(strweight);
                            account.setBirth(strbirth);
                            account.setSex(strsex);

                            mDatabaseRef.child("users").child(userId).setValue(account);

                            Toast.makeText(SignUpActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(i == R.id.r_btn1){
                strsex = "남성";
            }
            else if(i == R.id.r_btn2){
                strsex = "여성";
            }
        }
    };
}