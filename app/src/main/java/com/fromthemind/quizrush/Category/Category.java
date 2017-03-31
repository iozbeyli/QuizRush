package com.fromthemind.quizrush.Category;

import com.fromthemind.quizrush.Question.Question;

import java.util.Arrays;

/**
 * Created by Melih on 24.02.2017.
 */

public class Category {
    private String label;
    private Question[] questions;
    public Category(String label,Question[] questions){
        this.label=label;
        this.questions= questions;
    }

    @Override
    public String toString() {
        return "Category{" +
                "\nlabel='" + label + '\'' +
                ",\nquestions=" + Arrays.toString(questions) +
                "}\n";
    }

    public void addQuestion(Question question, int index){
        questions[index]=question;
    }

    public Question getQuestion(int index){
        return questions[index];
    }

    public void setLabel(String label){
        this.label=label;
    }

    public String getLabel(){
        return label;
    }

    public boolean repOK() {
        if(label == null || label.isEmpty())
            return false;

        for (Question obj:questions) {
            if (obj == null)
                return false;

            if(!obj.repOK())
                return false;
        }

        return true;
    }
}
