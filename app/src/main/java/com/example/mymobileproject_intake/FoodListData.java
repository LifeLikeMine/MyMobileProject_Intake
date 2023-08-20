package com.example.mymobileproject_intake;

import java.text.DecimalFormat;

public class FoodListData {
    public String f_once;
    public String f_cabo;
    public String f_fat;
    public String f_kcal;
    public String f_name;
    public String f_natrium;
    public String f_protein;
    public String f_sugar;

    public FoodListData() {
    }

    public FoodListData( String f_once, String f_cabo, String f_fat, String f_kcal, String f_name, String f_natrium, String f_protein, String f_sugar) {
        this.f_once = f_once;
        this.f_cabo = f_cabo;
        this.f_fat = f_fat;
        this.f_kcal = f_kcal;
        this.f_name = f_name;
        this.f_natrium = f_natrium;
        this.f_protein = f_protein;
        this.f_sugar = f_sugar;
    }

    public String getf_once() {
        return f_once;
    }

    public void setf_once(String f_once) {
        this.f_once = f_once;
    }

    public String getf_cabo() {
        return f_cabo;
    }

    public void setf_cabo(String f_cabo) {
        this.f_cabo = f_cabo;
    }

    public String getf_fat() {
        return f_fat;
    }

    public void setf_fat(String f_fat) {
        this.f_fat = f_fat;
    }

    public String getf_kcal() {
        return f_kcal;
    }

    public void setf_kcal(String f_kcal) {
        this.f_kcal = f_kcal;
    }

    public String getf_name() {
        return f_name;
    }

    public void setf_name(String f_name) {
        this.f_name = f_name;
    }

    public String getf_natrium() {
        return f_natrium;
    }

    public void setf_natrium(String f_natrium) {
        this.f_natrium = f_natrium;
    }

    public String getf_protein() {
        return f_protein;
    }

    public void setf_protein(String f_protein) {
        this.f_protein = f_protein;
    }

    public String getf_sugar() {
        return f_sugar;
    }

    public void setf_sugar(String f_sugar) {
        this.f_sugar = f_sugar;
    }

    public void setEat(double eat){

        double set = eat / Double.parseDouble(this.f_once);

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        this.f_once = Double.toString(eat);
        this.f_cabo = decimalFormat.format(Double.parseDouble(this.f_cabo) * set);
        this.f_fat = decimalFormat.format(Double.parseDouble(this.f_fat) * set);
        this.f_kcal = decimalFormat.format(Double.parseDouble(this.f_kcal) * set);
        this.f_natrium = decimalFormat.format(Double.parseDouble(this.f_natrium) * set);
        this.f_protein = decimalFormat.format(Double.parseDouble(this.f_protein) * set);
        this.f_sugar = decimalFormat.format(Double.parseDouble(this.f_sugar) * set);
    }
}
