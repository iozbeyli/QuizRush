package com.fromthemind.quizrush.Question;

/**
 * Created by Melih on 24.02.2017.
 */

public class Question {
    private int value = 0;
    private int time;
    private QuestionStatus status = QuestionStatus.ONSTART;

    public Question(int time, int value){
        this.time=time;
        this.value = value;
    }

    public int getValue(){return value;}

    public int getTime(){
        return time;
    }

    public void setStatus(QuestionStatus status){
        this.status=status;
    }

    public QuestionStatus getStatus(){
        return status;
    }

    public boolean repOK(){

        return true;
    }
}
