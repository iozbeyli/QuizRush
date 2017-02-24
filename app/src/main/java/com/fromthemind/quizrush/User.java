package com.fromthemind.quizrush;

/**
 * Created by Melih on 24.02.2017.
 */

public class User {
    private String nickname;
    private int score=0;
    private static User instance=null;
    public static User getInstance(){
        if(instance==null){
            instance=new User();
        }
        return instance;
    }
    private User(){}

    public void setNickname(String nickname){
        this.nickname=nickname;
    }

    public String getNickname(){
        return nickname;
    }

    public void setScore(int score){
        this.score=score;
    }

    public void addScore(int score){
        this.score+=score;
    }

    public int getScore(){
        return score;
    }

}
