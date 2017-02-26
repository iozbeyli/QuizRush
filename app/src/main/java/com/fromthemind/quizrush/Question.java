package com.fromthemind.quizrush;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;

import java.util.Arrays;

/**
 * Created by Melih on 24.02.2017.
 */

public class Question {
    private String definition;
    private String[] options;
    private int correctAnswerIndex;
    private int value = 0;
    private int time;

    private QuestionStatus status = QuestionStatus.ONSTART;

    public Question(int time, String definition, int value){
        this.time=time;
        this.definition = definition;
        this.value = value;
        options = new String[4];
    }

    public int getTime(){
        return time;
    }

    public String getDefinition(){
        return definition;
    }

    public int getValue(){return value;}

    public boolean isAnswer(int index){
        return correctAnswerIndex==index;
    }

    public void setOption(String option,int index,boolean isAnswer){
        options[index]=option;
        if(isAnswer){
            correctAnswerIndex=index;
        }
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

    public int getCorrectAnswer(){return correctAnswerIndex;}

    @Override
    public String toString() {
        return "Question{" +
                "\ndefinition='" + definition + '\'' +
                ",\noptions=" + Arrays.toString(options) +
                ",\ncorrectAnswerIndex=" + correctAnswerIndex +
                ",\ntime=" + time +
                ",\nstatus=" + status +
                ",\nvalue="+value+
                "}\n";
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
