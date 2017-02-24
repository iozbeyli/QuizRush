package com.fromthemind.quizrush;

/**
 * Created by Melih on 24.02.2017.
 */

public class Question {
    private String definition;
    private String[] options;
    private int correctAnswerIndex;
    private int time;

    QuestionStatus status=QuestionStatus.ONSTART;

    public Question(int time){
        this.time=time;
        options = new String[4];
    }

    public void setTime(int time){
        this.time=time;
    }

    public int getTime(){
        return time;
    }

    public void setOption(String option,int index,boolean isAnswer){
        options[index]=option;
        if(isAnswer){
            correctAnswerIndex=index;
        }
    }

    public boolean isAnswer(int index){
        return correctAnswerIndex==index;
    }

    public String[] getOptions(){
        return options;
    }

    public void setStatus(QuestionStatus status){
        this.status=status;
    }

    public QuestionStatus getStatus(){
        return status;
    }

}
