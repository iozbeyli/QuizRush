package com.fromthemind.quizrush;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Melih on 24.02.2017.
 */

public class User {


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public ArrayList<String> friends = new ArrayList<String>();
    public String username;
    public String name;
    public String surname;
    public String city;
    public String password;
    public int score=0;
    public int lives = 4;
    public int memoLevel = 4;

    private static User instance=null;
    public static User getInstance(){
        if(instance==null){
            instance=new User();
        }
        return instance;
    }
    public static void setInstance(User user){
        instance=user;
    }
    private User(){}
    public User(String username,String name,String surname,String city, String password , int score, int lives,int memoLevel){
        this.username=username;
        this.name=name;
        this.surname=surname;
        this.city=city;
        this.password=password;
        this.score=score;
        this.lives=lives;
        this.memoLevel=memoLevel;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected static void deleteUser(){
        instance=null;
    }


    public void setMemoLevel(int memoLevel) {
        this.memoLevel = memoLevel;
    }

    protected void addScore(int score){
        this.score+=score;
    }
    public int getScore(){
        return score;
    }

    public int getLives(){
        return lives;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(int score) {
        this.score = score;
    }

    protected void loseLife(){
        lives-=1;
    }

    protected void levelUpMemo(){
        memoLevel++;
        resetLives();
    }

    public int getMemoLevel(){
        return memoLevel;
    }

    protected void resetLives(){
        lives=4;
    }

    public boolean repOK() {
        if(username == null || username.isEmpty())
            return false;

        if(score<0)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", score=" + score +
                ", lives=" + lives +
                ", memoLevel=" + memoLevel +
                '}';
    }

    public static void updateInstance(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");
        Query queryRef = userRef.orderByChild("username").equalTo(instance.username);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null){
                    Log.wtf("username", "error for friend listing");

                }
                else {
                    User the = snapshot.child(instance.username).getValue(User.class);

                    User.setInstance(the);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
