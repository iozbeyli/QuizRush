package com.fromthemind.quizrush;

import java.util.Arrays;

/**
 * Created by Melih on 24.02.2017.
 */

public class Question {
    private String definition;
    private String[] options;
    private int correctAnswerIndex;
    private int time;

    QuestionStatus status=QuestionStatus.ONSTART;

    public Question(int time, String definition){
        this.time=time;
        this.definition = definition;
        options = new String[4];
    }

    @Override
    public String toString() {
        return "Question{" +
                "\ndefinition='" + definition + '\'' +
                ",\noptions=" + Arrays.toString(options) +
                ",\ncorrectAnswerIndex=" + correctAnswerIndex +
                ",\ntime=" + time +
                ",\nstatus=" + status +
                "}\n";
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

    public boolean repOK() {
        if(definition == null || definition.isEmpty())
            return false;

        if(status == null)
            return false;

        if(time < 0)
            return false;

        if(correctAnswerIndex<0 || correctAnswerIndex>3)
            return false;

        for (String obj:options) {
            if (obj == null || obj.isEmpty())
                return false;
        }

        return true;
    }
}
