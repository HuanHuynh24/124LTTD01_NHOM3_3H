package com.example.a124lttd01_nhom3.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a124lttd01_nhom3.FakeData.User;
import com.google.gson.Gson;

import java.util.List;

public class User_Gson {
    private Context context;
    private List<User> users;

    public User_Gson(Context context) {

        this.context = context;
    }



    public void saveUser(User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(user);

        editor.putString("userObject", json);
        editor.apply();
    }

    public User getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userObject", null);

        if (json != null) {
            User user = gson.fromJson(json, User.class);
            return user;
        } else {
            return null;
        }
    }
}
