package com.fromthemind.quizrush;


import android.util.Log;

/**
 * Created by Melih on 24.02.2017.
 */

public class User {
    private String nickname;
    private int score=0;
    private int lives = 4;
    private int memoLevel = 4;
    private static User instance=null;
    public static User getInstance(){
        if(instance==null){
            instance=new User();
        }
        return instance;
    }
    private User(){}

    protected static void deleteUser(){
        instance=null;
    }

    protected void setNickname(String nickname){
        this.nickname=nickname;
    }

    protected String getNickname(){
        return nickname;
    }

    protected void setScore(int score){
        this.score=score;
    }

    protected void addScore(int score){
        this.score+=score;
    }

    protected int getScore(){
        return score;
    }

    protected int getLives(){
        return lives;
    }

    protected void loseLife(){
        lives-=1;
    }

    protected void levelUpMemo(){
        memoLevel++;
        resetLives();
    }

    protected int getMemoLevel(){
        return memoLevel;
    }

    protected void resetLives(){
        lives=4;
    }

    public boolean repOK() {
        if(nickname == null || nickname.isEmpty())
            return false;

        if(score<0)
            return false;

        return true;
    }
}
