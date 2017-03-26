package com.fromthemind.quizrush.Category;

import com.fromthemind.quizrush.Question.Question;

import java.util.Arrays;

/**
 * Created by Melih on 24.02.2017.
 */

public class Category {
    private String topic;
    private Question[] questions;
    public Category(String topic,int numberOfQuestions){
        this.topic=topic;
        questions=new Question[numberOfQuestions];
    }

    @Override
    public String toString() {
        return "Category{" +
                "\ntopic='" + topic + '\'' +
                ",\nquestions=" + Arrays.toString(questions) +
                "}\n";
    }

    public void addQuestion(Question question, int index){
        questions[index]=question;
    }

    public Question getQuestion(int index){
        return questions[index];
    }

    public void setTopic(String topic){
        this.topic=topic;
    }

    public String getTopic(){
        return topic;
    }

    public boolean repOK() {
        if(topic == null || topic.isEmpty())
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
