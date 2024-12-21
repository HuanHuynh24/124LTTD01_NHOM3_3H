package com.example.a124lttd01_nhom3.FakeData;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private String id_user;
    private String username;
    private String password;
    private String email;
    private int education_level;
    private String avatar_img;
    private boolean gender;
    private Date date_of_birth;

    public User() {
    }

    public User(String id_user, String username, String password, String email, int education_level, String avatar_img, boolean gender, Date date_of_birth) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.education_level = education_level;
        this.avatar_img = avatar_img;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.id_user = id_user;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEducation_level() {
        return education_level;
    }

    public void setEducation_level(int education_level) {
        this.education_level = education_level;
    }

    public String getAvatar_img() {
        return avatar_img;
    }

    public void setAvatar_img(String avatar_img) {
        this.avatar_img = avatar_img;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user='" + id_user + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", education_level=" + education_level +
                ", avatar_img='" + avatar_img + '\'' +
                ", gender=" + gender +
                ", date_of_birth=" + date_of_birth +
                '}';
    }
}
