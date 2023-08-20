package com.example.mymobileproject_intake;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DecimalFormat;
import java.util.Calendar;

@IgnoreExtraProperties
public class User  {
    private String idToken; //고유 아이디 키 값
    private String emailId;
    private String password;
    private String name;
    private String tall;
    private String weight;
    private String birth;
    private String sex;
    private String data;

    public User() { } //생성자

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTall() {
        return tall;
    }

    public void setTall(String tall) {
        this.tall = tall;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getData(){ return data; }

    public void setData(String data){ this.data = data;}
    public String getKcal(){
        double kgAvg ,kcal;
        kgAvg = (Double.parseDouble(this.tall) - 100) * 0.9;
        kcal = kgAvg * 35;

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(kcal);
    }
    public String getCabo(){
        double Cabo;

        Cabo = Double.parseDouble(getKcal())*0.4/4;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(Cabo);
    }
    public String getPro(){
        double Pro;

        Pro = Double.parseDouble(getKcal())*0.4/4;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(Pro);
    }
    public String getFat(){
        double Fat;

        Fat = Double.parseDouble(getKcal())*0.2/9;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(Fat);
    }
    public String getNat(){
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(2000);
    }
    public String getSug(){
        double Sug;
        Sug = Double.parseDouble(getKcal()) * 0.025;

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(Sug);
    }
}