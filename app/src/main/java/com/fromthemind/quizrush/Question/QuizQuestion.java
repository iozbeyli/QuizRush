package com.fromthemind.quizrush.Question;

import java.util.Arrays;

/**
 * Created by Melih on 24.03.2017.
 */

public class QuizQuestion extends Question{
    private String definition;
    private String[] options;
    private int correctAnswerIndex;

    public QuizQuestion(int time, String definition, int value){
        super(time, value);
        this.definition = definition;
        options = new String[4];
    }

    public String getDefinition(){
        return definition;
    }

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

    public int getCorrectAnswer(){return correctAnswerIndex;}

    @Override
    public String toString() {
        return "Question{" +
                "\ndefinition='" + definition + '\'' +
                ",\noptions=" + Arrays.toString(options) +
                ",\ncorrectAnswerIndex=" + correctAnswerIndex +
                ",\ntime=" + getTime() +
                ",\nstatus=" + getStatus() +
                ",\nvalue="+getValue()+
                "}\n";
    }

    public boolean repOK() {
        if(definition == null || definition.isEmpty())
            return false;

        if(getStatus() == null)
            return false;

        if(getTime() < 0)
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
